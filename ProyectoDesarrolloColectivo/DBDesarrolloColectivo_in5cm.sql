drop database if exists DBDesarrolloColectivo_in5cm;
create database DBDesarrolloColectivo_in5cm;
use DBDesarrolloColectivo_in5cm;

create table Usuario(
    id_usuario int primary key auto_increment,
    nombre_completo varchar(150),
    correo_usuario varchar(100) unique not null,
    contraseña varchar(100)
);

create table Frase_motivadora(
    id_frase_motivadora int primary key auto_increment,
    texto text,
    autor varchar(50)
);

create table Registro_sueño(
    id_registro_sueño int primary key auto_increment,
    fecha_sueño date,
    horas_dormidas decimal(4,2),
    calidad_sueño varchar(100),
    fk_id_usuario int not null,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade
);

create table Objetivo_meditacion(
    id_objetivo_meditacion int primary key auto_increment,
    tiempo_objetivo varchar(50),
    dias_objetivo int,
    fk_id_usuario int not null,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade
);

create table Perfil_nutricional(
    id_perfil_nutricional int primary key auto_increment,
    peso_kg int,
    altura decimal(4,2),
    edad int,
    genero enum('Femenino','Masculino'),
    nivel_actividad enum('bajo','medio','alto'),
    objetivo varchar(75),
    fk_id_usuario int not null,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade
);

create table Libro(
    id_libro int primary key auto_increment,
    titulo_libro varchar(100),
    autor_libro varchar(100),
    estado enum('pendiente','leyendo','terminado'),
    cantidad_pag int,
    cantidad_leido int,
    fk_id_usuario int not null,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade
);

create table Objetivos(
    id_objetivos int primary key auto_increment,
    titulo_objetivo varchar(40),
    descripcion_objetivo varchar(150),
    estado_objetivo varchar(45),
    fecha_objetivo date,
    fk_id_usuario int not null,
    fk_id_frase_motivadora int,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade,
    foreign key(fk_id_frase_motivadora) references Frase_motivadora(id_frase_motivadora) on delete cascade
);

create table Apoyo_emocional(
    id_apoyo_emocional int primary key auto_increment,
    titulo varchar(50),
    categoria varchar(50),
    contenido text,
    nivel_animo enum('Mal','Bien','Mas o menos'),
    fk_id_usuario int not null,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade
);

create table Rutina(
    id_rutina int primary key auto_increment,
    nombre_rutina varchar(50),
    dias_semana varchar(100),
    fk_id_usuario int not null,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade
);

create table Ejercicio(
    id_ejercicio int primary key auto_increment,
    nombre_ejercicio varchar(60),
    series_ejercicio int,
    repeticiones_ejercicio int,
    tiempo_ejercicio int,
    descanso_ejercicio int,
    fk_id_rutina int not null,
    foreign key(fk_id_rutina) references Rutina(id_rutina) on delete cascade
);

create table Entrada_diario(
    id_entrada_diario int primary key auto_increment,
    fecha date,
    que_paso text,
    plan_mañana text,
    reflexion text,
    fk_id_usuario int not null,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade
);

create table Registro_meditacion(
    id_registro_meditacion int primary key auto_increment,
    tipo_meditacion enum('Guiada','Respiracion','Mindfulness','BodyScan'),
    duracion_minutos int,
    nivel_dificultad enum('Principiante','Intermedio','Avanzado'),
    fecha_registro datetime default current_timestamp,
    fk_id_usuario int not null,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade
);

create table Racha_ejercicio(
    id_racha_ejercicio int primary key auto_increment,
    dias_consecutivos int default 0,
    fecha date,
    fk_id_usuario int not null,
    fk_id_frase_motivadora int,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade,
    foreign key(fk_id_frase_motivadora) references Frase_motivadora(id_frase_motivadora) on delete cascade
);

create table Racha_lectura(
    id_racha_lectura int primary key auto_increment,
    dias_consecutivos int default 0,
    fecha date,
    fk_id_usuario int not null,
    fk_id_frase_motivadora int,
    foreign key(fk_id_usuario) references Usuario(id_usuario) on delete cascade,
    foreign key(fk_id_frase_motivadora) references Frase_motivadora(id_frase_motivadora) on delete cascade
);

alter table Usuario
add column rol enum ('ADMIN', 'USER') default 'USER';
-- ======================================================= PROCEDIMIENTOS ALMACENADOS ======================================================= --

-- =================== usuario ================= --
delimiter $$
create procedure sp_listarUsuario()
begin
    select * from Usuario order by id_usuario;
end $$
delimiter ;

-- Agregar usuario --
delimiter $$
create procedure sp_agregarUsuario(
    in p_nombre_completo varchar(150),
    in p_correo_usuario varchar(100),
    in p_contraseña varchar(100)
)
begin
    insert into Usuario(nombre_completo,correo_usuario,contraseña)
    values(p_nombre_completo,p_correo_usuario,p_contraseña);
end $$
delimiter ;

call sp_agregarUsuario('Kevin Lancerio',   'kevin@gmail.com',   '123');
call sp_agregarUsuario('Luis Pérez',      'luis@gmail.com',    '545');
call sp_agregarUsuario('Enrique Torres',  'enrique@gmail.com', '526');
call sp_agregarUsuario('María López',     'maria@gmail.com',   'abc');
call sp_agregarUsuario('Andrea Fuentes',  'andrea@gmail.com',  'pass1');
call sp_agregarUsuario('Carlos Méndez',   'carlos@gmail.com',  'pass2');
call sp_agregarUsuario('Sofía Castillo',  'sofia@gmail.com',   'pass3');
call sp_agregarUsuario('Diego Herrera',   'diego@gmail.com',   'pass4');
call sp_agregarUsuario('Valeria Ruiz',    'valeria@gmail.com', 'pass5');
call sp_agregarUsuario('Javier Morales',  'javier@gmail.com',  'pass6');

update Usuario
set rol = 'ADMIN'
where correo_usuario = 'kevin@gmail.com';

update Usuario
set rol = 'USER'
where correo_usuario <> 'kevin@gmail.com';

-- Editar usuario --
delimiter $$
create procedure sp_editarUsuario(
    in p_id_usuario int,
    in p_nombre_completo varchar(150),
    in p_correo_usuario varchar(100),
    in p_contraseña varchar(100)
)
begin
    update Usuario
    set nombre_completo = p_nombre_completo, correo_usuario = p_correo_usuario, contraseña = p_contraseña
    where id_usuario=p_id_usuario;
end $$
delimiter ;

-- Eliminar usuario --
delimiter $$
create procedure sp_eliminarUsuario(
    in p_id_usuario int
)
begin
    delete from Usuario where id_usuario=p_id_usuario;
end $$
delimiter ;


-- ================= perfil_nutricional =============== --
delimiter $$
create procedure sp_agregarPerfil_nutricional(
    in p_peso_kg int,
    in p_altura decimal(4,2),
    in p_edad int,
    in p_genero varchar(20),
    in p_nivel_actividad varchar(20),
    in p_objetivo varchar(75),
    in p_fk_id_usuario int
)
begin
    insert into Perfil_nutricional(peso_kg,altura,edad,genero,nivel_actividad,objetivo,fk_id_usuario)
    values(p_peso_kg,p_altura,p_edad,p_genero,p_nivel_actividad,p_objetivo,p_fk_id_usuario);
end $$
delimiter ;

call sp_agregarPerfil_nutricional(70, 1.75, 25, 'Masculino', 'medio', 'Mantener peso',  1);
call sp_agregarPerfil_nutricional(55, 1.60, 22, 'Femenino',  'alto',  'Bajar de peso',  2);
call sp_agregarPerfil_nutricional(80, 1.80, 30, 'Masculino', 'bajo',  'Ganar músculo',  3);
call sp_agregarPerfil_nutricional(62, 1.65, 28, 'Femenino',  'medio', 'Mantener peso',  4);
call sp_agregarPerfil_nutricional(90, 1.85, 35, 'Masculino', 'alto',  'Bajar de peso',  5);
call sp_agregarPerfil_nutricional(48, 1.55, 20, 'Femenino',  'bajo',  'Subir de peso',  6);
call sp_agregarPerfil_nutricional(75, 1.78, 27, 'Masculino', 'medio', 'Ganar músculo',  7);
call sp_agregarPerfil_nutricional(58, 1.62, 24, 'Femenino',  'alto',  'Mantener peso',  8);
call sp_agregarPerfil_nutricional(85, 1.82, 32, 'Masculino', 'bajo',  'Bajar de peso',  9);
call sp_agregarPerfil_nutricional(60, 1.68, 26, 'Femenino',  'medio', 'Ganar músculo',  10);

delimiter $$
create procedure sp_editarPerfil_nutricional(
    in p_id_perfil_nutricional int,
    in p_peso_kg int,
    in p_altura decimal(4,2),
    in p_edad int,
    in p_genero varchar(20),
    in p_nivel_actividad varchar(20),
    in p_objetivo varchar(75),
    in p_fk_id_usuario int
)
begin
    update Perfil_nutricional
    set peso_kg=p_peso_kg, altura=p_altura, edad=p_edad, genero=p_genero, nivel_actividad=p_nivel_actividad, objetivo=p_objetivo, fk_id_usuario=p_fk_id_usuario
    where id_perfil_nutricional=p_id_perfil_nutricional;
end $$
delimiter ;

delimiter $$
create procedure sp_listarPerfil_nutricional()
begin
    select * from Perfil_nutricional order by id_perfil_nutricional;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarPerfil_nutricional(
    in p_id_perfil_nutricional int
)
begin
    delete from Perfil_nutricional where id_perfil_nutricional=p_id_perfil_nutricional;
end $$
delimiter ;


-- ================= libro ================ --
delimiter $$
create procedure sp_listarLibro()
begin
    select * from Libro order by id_libro;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarLibro(
    in p_titulo_libro varchar(100),
    in p_autor_libro varchar(100),
    in p_estado varchar(20),
    in p_cantidad_pag int,
    in p_cantidad_leido int,
    in p_fk_id_usuario int
)
begin
    insert into Libro(titulo_libro,autor_libro,estado,cantidad_pag,cantidad_leido,fk_id_usuario)
    values(p_titulo_libro,p_autor_libro,p_estado,p_cantidad_pag,p_cantidad_leido,p_fk_id_usuario);
end $$
delimiter ;

call sp_agregarLibro('Hábitos Atómicos',              'James Clear',   'leyendo',   320, 150, 1);
call sp_agregarLibro('El Poder del Ahora',             'Eckhart Tolle', 'terminado', 280, 280, 2);
call sp_agregarLibro('Mindset',                        'Carol Dweck',   'pendiente', 300,   0, 3);
call sp_agregarLibro('El Monje que vendió su Ferrari', 'Robin Sharma',  'leyendo',   250, 100, 4);
call sp_agregarLibro('Los 7 Hábitos',                  'Stephen Covey', 'terminado', 400, 400, 5);
call sp_agregarLibro('Ikigai',                         'Héctor García', 'pendiente', 200,   0, 6);
call sp_agregarLibro('El Alquimista',                  'Paulo Coelho',  'terminado', 190, 190, 7);
call sp_agregarLibro('Fluir',                          'Mihaly C.',     'leyendo',   340, 200, 8);
call sp_agregarLibro('Deep Work',                      'Cal Newport',   'pendiente', 310,   0, 9);
call sp_agregarLibro('La Magia del Orden',             'Marie Kondo',   'leyendo',   226,  80, 10);

delimiter $$
create procedure sp_editarLibro(
    in p_id_libro int,
    in p_titulo_libro varchar(100),
    in p_autor_libro varchar(100),
    in p_estado varchar(20),
    in p_cantidad_pag int,
    in p_cantidad_leido int,
    in p_fk_id_usuario int
)
begin
    update Libro
    set titulo_libro=p_titulo_libro, autor_libro=p_autor_libro, estado=p_estado, cantidad_pag=p_cantidad_pag, cantidad_leido=p_cantidad_leido, fk_id_usuario=p_fk_id_usuario
    where id_libro=p_id_libro;
    
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarLibro(
    in p_id_libro int
)
begin
    delete from Libro where id_libro=p_id_libro;
end $$
delimiter ;

-- ==================== rutina ================== --
delimiter $$
create procedure sp_listarRutina()
begin
    select * from Rutina order by id_rutina;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarRutina(
    in p_nombre_rutina varchar(50),
    in p_dias_semana varchar(100),
    in p_fk_id_usuario int
)
begin
    insert into Rutina(nombre_rutina,dias_semana,fk_id_usuario)
    values(p_nombre_rutina,p_dias_semana,p_fk_id_usuario);
end $$
delimiter ;

call sp_agregarRutina('Rutina de Fuerza',    'Lunes,Miércoles,Viernes', 1);
call sp_agregarRutina('Cardio Matutino',     'Martes,Jueves',           2);
call sp_agregarRutina('Yoga y Flexibilidad', 'Lunes,Miércoles',         3);
call sp_agregarRutina('Full Body',           'Lunes,Jueves',            4);
call sp_agregarRutina('Rutina de Espalda',   'Martes,Viernes',          5);
call sp_agregarRutina('Pierna y Glúteo',     'Lunes,Miércoles,Sábado',  6);
call sp_agregarRutina('Core y Abdomen',      'Todos los días',          7);
call sp_agregarRutina('HIIT Intensivo',      'Martes,Jueves,Sábado',    8);
call sp_agregarRutina('Rutina de Pecho',     'Lunes,Viernes',           9);
call sp_agregarRutina('Movilidad y Postura', 'Miércoles,Sábado',        10);

delimiter $$
create procedure sp_editarRutina(
    in p_id_rutina int,
    in p_nombre_rutina varchar(50),
    in p_dias_semana varchar(100),
    in p_fk_id_usuario int
)
begin
    update Rutina
    set nombre_rutina=p_nombre_rutina, dias_semana=p_dias_semana, fk_id_usuario=p_fk_id_usuario
    where id_rutina=p_id_rutina;
    
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarRutina(
    in p_id_rutina int
)
begin
    delete from Rutina where id_rutina=p_id_rutina;
end $$
delimiter ;


-- ==================== ejercicio ================== --
delimiter $$
create procedure sp_listarEjercicio()
begin
    select * from Ejercicio order by id_ejercicio;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarEjercicio(
    in p_nombre_ejercicio varchar(60),
    in p_series_ejercicio int,
    in p_repeticiones_ejercicio int,
    in p_tiempo_ejercicio int,
    in p_descanso_ejercicio int,
    in p_fk_id_rutina int
)
begin
    insert into Ejercicio(nombre_ejercicio,series_ejercicio,repeticiones_ejercicio,tiempo_ejercicio,descanso_ejercicio,fk_id_rutina)
    values(p_nombre_ejercicio,p_series_ejercicio,p_repeticiones_ejercicio,p_tiempo_ejercicio,p_descanso_ejercicio,p_fk_id_rutina);
end $$
delimiter ;


call sp_agregarEjercicio('Sentadillas',       4, 12, 0,  60, 1);
call sp_agregarEjercicio('Press de Banca',    4, 10, 0,  90, 1);
call sp_agregarEjercicio('Correr',            1,  1, 30,  0, 2);
call sp_agregarEjercicio('Bicicleta',         1,  1, 20,  0, 2);
call sp_agregarEjercicio('Postura del Árbol', 3,  1, 60, 30, 3);
call sp_agregarEjercicio('Peso Muerto',       4, 10, 0,  90, 4);
call sp_agregarEjercicio('Remo con Barra',    4, 12, 0,  60, 5);
call sp_agregarEjercicio('Prensa de Pierna',  4, 15, 0,  60, 6);
call sp_agregarEjercicio('Plancha',           3,  1, 60, 30, 7);
call sp_agregarEjercicio('Burpees',           4, 15, 0,  45, 8);

delimiter $$
create procedure sp_editarEjercicio(
    in p_id_ejercicio int,
    in p_nombre_ejercicio varchar(60),
    in p_series_ejercicio int,
    in p_repeticiones_ejercicio int,
    in p_tiempo_ejercicio int,
    in p_descanso_ejercicio int,
    in p_fk_id_rutina int
)
begin
    update Ejercicio
    set nombre_ejercicio=p_nombre_ejercicio,
        series_ejercicio=p_series_ejercicio,
        repeticiones_ejercicio=p_repeticiones_ejercicio,
        tiempo_ejercicio=p_tiempo_ejercicio,
        descanso_ejercicio=p_descanso_ejercicio,
        fk_id_rutina=p_fk_id_rutina
    where id_ejercicio=p_id_ejercicio;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarEjercicio(
    in p_id_ejercicio int
)
begin
    delete from Ejercicio where id_ejercicio=p_id_ejercicio;
end $$
delimiter ;


-- =============== frase_motivadora ============= --

delimiter $$
create procedure sp_listarFrase_motivadora()
begin
    select * from Frase_motivadora order by id_frase_motivadora;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarFrase_motivadora(
    in p_texto text,
    in p_autor varchar(50)
)
begin
    insert into Frase_motivadora(texto,autor)
    values(p_texto,p_autor);
end $$
delimiter ;

call sp_agregarFrase_motivadora('El éxito es la suma de pequeños esfuerzos repetidos día tras día.',       'Robert Collier');
call sp_agregarFrase_motivadora('No cuentes los días, haz que los días cuenten.',                          'Muhammad Ali');
call sp_agregarFrase_motivadora('La disciplina es el puente entre metas y logros.',                        'Jim Rohn');
call sp_agregarFrase_motivadora('Cree en ti mismo y todo será posible.',                                   'Anónimo');
call sp_agregarFrase_motivadora('El único modo de hacer un gran trabajo es amar lo que haces.',            'Steve Jobs');
call sp_agregarFrase_motivadora('Cada día es una nueva oportunidad para mejorar.',                         'Anónimo');
call sp_agregarFrase_motivadora('Tu único límite eres tú mismo.',                                          'Anónimo');
call sp_agregarFrase_motivadora('El camino de mil millas comienza con un solo paso.',                      'Lao Tzu');
call sp_agregarFrase_motivadora('Haz de cada día tu obra maestra.',                                        'John Wooden');
call sp_agregarFrase_motivadora('No te detengas cuando estés cansado, detente cuando hayas terminado.',    'David Goggins');

delimiter $$
create procedure sp_editarFrase_motivadora(
    in p_id_frase_motivadora int,
    in p_texto text,
    in p_autor varchar(50)
)
begin
    update Frase_motivadora
    set texto=p_texto,
        autor=p_autor
    where id_frase_motivadora=p_id_frase_motivadora;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarFrase_motivadora(
    in p_id_frase_motivadora int
)
begin
    delete from Frase_motivadora where id_frase_motivadora=p_id_frase_motivadora;
end $$
delimiter ;

delimiter $$
create procedure sp_listar_frase_motivadora()
begin
    select * from Frase_motivadora;
end $$
delimiter ;

delimiter $$
create procedure sp_read_by_id_frase_motivadora(
    in p_id_frase_motivadora int
)
begin
    select * from Frase_motivadora where id_frase_motivadora = p_id_frase_motivadora;
end $$
delimiter ;


-- =============== racha_ejercicio ============== --
delimiter $$
create procedure sp_agregar_racha_ejercicio(
    in p_fecha date,
    in p_fk_id_usuario int
)
begin
    insert into Racha_ejercicio (fecha, fk_id_usuario)
    values (p_fecha, p_fk_id_usuario);
end $$
delimiter ;

call sp_agregar_racha_ejercicio('2025-01-01', 1);
call sp_agregar_racha_ejercicio('2025-01-02', 1);
call sp_agregar_racha_ejercicio('2025-01-03', 2);
call sp_agregar_racha_ejercicio('2025-01-04', 3);
call sp_agregar_racha_ejercicio('2025-01-05', 4);
call sp_agregar_racha_ejercicio('2025-01-06', 5);
call sp_agregar_racha_ejercicio('2025-01-07', 6);
call sp_agregar_racha_ejercicio('2025-01-08', 7);
call sp_agregar_racha_ejercicio('2025-01-09', 8);
call sp_agregar_racha_ejercicio('2025-01-10', 9);


-- ============== racha_lectura ============= --
delimiter $$
create procedure sp_agregar_racha_lectura(
    in p_fecha date,
    in p_fk_id_usuario int
)
begin
    insert into Racha_lectura (fecha, fk_id_usuario)
    values (p_fecha, p_fk_id_usuario);
end $$
delimiter ;

call sp_agregar_racha_lectura('2025-01-01',  1);
call sp_agregar_racha_lectura('2025-01-02',  2);
call sp_agregar_racha_lectura('2025-01-03',  3);
call sp_agregar_racha_lectura('2025-01-04',  4);
call sp_agregar_racha_lectura('2025-01-05',  5);
call sp_agregar_racha_lectura('2025-01-06',  6);
call sp_agregar_racha_lectura('2025-01-07',  7);
call sp_agregar_racha_lectura('2025-01-08',  8);
call sp_agregar_racha_lectura('2025-01-09',  9);
call sp_agregar_racha_lectura('2025-01-10', 10);


-- ============= objetivos ================== --
delimiter $$
create procedure sp_agregarObjetivos(
	in p_titulo_objetivo varchar(40),
    in p_descripcion_objetivo varchar(150),
    in p_estado_objetivo varchar(45),
    in p_fecha_objetivo date,
    in p_fk_id_usuario int,
    in p_fk_id_frase_motivadora int
)
begin
    insert into Objetivos(titulo_objetivo,descripcion_objetivo,estado_objetivo,fecha_objetivo,fk_id_usuario,fk_id_frase_motivadora)
    values(p_titulo_objetivo,p_descripcion_objetivo,p_estado_objetivo,p_fecha_objetivo,p_fk_id_usuario,p_fk_id_frase_motivadora);
end $$
delimiter ;

call sp_agregarObjetivos('Meditación','Meditar 10 minutos diarios',     'En progreso', '2025-03-01',  1,1);
call sp_agregarObjetivos('Lectura','Leer 2 libros al mes',           'Pendiente',   '2025-04-01',  2,2);
call sp_agregarObjetivos('Maraton','Correr 5km sin parar',           'Completado',  '2025-02-15',  3,3);
call sp_agregarObjetivos('Descansar','Dormir 8 horas cada noche',      'En progreso', '2025-05-01',  4,4);
call sp_agregarObjetivos('Salud','Reducir el consumo de azúcar',   'Pendiente',   '2025-06-01',  5,5);
call sp_agregarObjetivos('Escritura','Escribir en el diario cada día', 'En progreso', '2025-03-15',  6,6);
call sp_agregarObjetivos('Ejercitarce','Hacer ejercicio 4 días/semana',  'Completado',  '2025-01-31',  7,7);
call sp_agregarObjetivos('Cocinar','Aprender a cocinar saludable',   'Pendiente',   '2025-07-01',  8,8);
call sp_agregarObjetivos('Desestrezar','Reducir el estrés laboral',      'En progreso', '2025-04-30',  9,9);
call sp_agregarObjetivos('Hidratarce','Tomar agua suficiente al día',   'Completado',  '2025-02-01',  10,10);

delimiter $$
create procedure sp_actualizarObjetivos(
    in p_id_objetivos int,
    in p_titulo_objetivo varchar(40),
    in p_descripcion_objetivo varchar(150),
    in p_estado_objetivo varchar(45),
    in p_fecha_objetivo date,
    in p_fk_id_usuario int
)
begin
    update Objetivos
    set titulo_objetivo = p_titulo_objetivo, descripcion_objetivo = p_descripcion_objetivo, estado_objetivo = p_estado_objetivo, fecha_objetivo = p_fecha_objetivo, fk_id_usuario = p_fk_id_usuario
    where id_objetivos=p_id_objetivos;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarObjetivos(
    in p_id_objetivos int
)
begin
    delete from Objetivos where id_objetivos=p_id_objetivos;
end $$
delimiter ;

delimiter $$
create procedure sp_listar_objetivos()
begin
    select * from objetivos;
end $$
delimiter ;

delimiter $$
create procedure sp_read_by_id_objetivos(
    in p_id_objetivos int
)
begin
    select * from objetivos where id_objetivos = p_id_objetivos;
end $$
delimiter ;


-- ==================== apoyo_emocional ================== --
delimiter $$
create procedure sp_listarApoyo_emocional()
begin
    select * from Apoyo_emocional order by id_apoyo_emocional;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarApoyo_emocional(
    in p_titulo varchar(50),
    in p_categoria varchar(50),
    in p_contenido text,
    in p_nivel_animo varchar(20),
    in p_fk_id_usuario int
)
begin
    insert into Apoyo_emocional(titulo,categoria,contenido,nivel_animo,fk_id_usuario)
    values(p_titulo,p_categoria,p_contenido,p_nivel_animo,p_fk_id_usuario);
end $$
delimiter ;

call sp_agregarApoyo_emocional('Respiración 4-7-8',       'Ansiedad',   'Inhala 4s, retén 7s, exhala 8s. Repite 3 veces.',  'Mal',          1);
call sp_agregarApoyo_emocional('Gratitud diaria',          'Bienestar',  'Escribe 3 cosas por las que estás agradecido.',    'Bien',         2);
call sp_agregarApoyo_emocional('Caminata consciente',      'Estrés',     'Camina 10 minutos prestando atención a tus pasos.','Mas o menos',  3);
call sp_agregarApoyo_emocional('Journaling emocional',     'Tristeza',   'Escribe cómo te sientes sin juzgarte.',            'Mal',          4);
call sp_agregarApoyo_emocional('Música relajante',         'Ansiedad',   'Escucha sonidos naturales durante 15 minutos.',    'Mas o menos',  5);
call sp_agregarApoyo_emocional('Técnica 5-4-3-2-1',        'Estrés',     'Nombra 5 cosas que ves, 4 que tocas, 3 oyes...',  'Mal',          6);
call sp_agregarApoyo_emocional('Afirmaciones positivas',   'Autoestima', 'Repite: Soy capaz, soy valioso, lo lograré.',      'Bien',         7);
call sp_agregarApoyo_emocional('Descanso digital',         'Bienestar',  'Apaga pantallas 1 hora antes de dormir.',          'Mas o menos',  8);
call sp_agregarApoyo_emocional('Meditación guiada 10 min', 'Ansiedad',   'Sigue una sesión guiada en YouTube o Spotify.',    'Mal',          9);
call sp_agregarApoyo_emocional('Conexión social',          'Tristeza',   'Llama o visita a alguien que te importe.',         'Bien',         10);

delimiter $$
create procedure sp_editarApoyo_emocional(
    in p_id_apoyo_emocional int,
    in p_titulo varchar(50),
    in p_categoria varchar(50),
    in p_contenido text,
    in p_nivel_animo varchar(20),
    in p_fk_id_usuario int
)
begin
    update Apoyo_emocional
    set titulo = p_titulo, categoria = p_categoria, contenido = p_contenido, nivel_animo = p_nivel_animo
    where id_apoyo_emocional=p_id_apoyo_emocional;
    
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarApoyo_emocional(
    in p_id_apoyo_emocional int
)
begin
    delete from Apoyo_emocional where id_apoyo_emocional=p_id_apoyo_emocional;
end $$
delimiter ;

delimiter $$
create procedure sp_read_all_apoyo_emocional()
begin
    select * from apoyo_emocional;
end $$
delimiter ;

delimiter $$
create procedure sp_read_by_id_apoyo_emocional(
    in p_id_apoyo_emocional int
)
begin
    select * from apoyo_emocional where id_apoyo_emocional = p_id_apoyo_emocional;
end $$
delimiter ;

-- =========================== registro_sueno ============================ --

-- Listar registro_sueno
delimiter $$
create procedure sp_listarRegistro_sueño()
begin
    select * from Registro_sueño order by id_registro_sueño;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarRegistro_sueño(
    in p_fecha_sueño date,
    in p_horas_dormidas decimal(4,2),
    in p_calidad_sueño varchar(100),
    in p_fk_id_usuario int
)
begin
    insert into Registro_sueño(fecha_sueño,horas_dormidas,calidad_sueño,fk_id_usuario)
    values(p_fecha_sueño,p_horas_dormidas,p_calidad_sueño,p_fk_id_usuario);
end $$
delimiter ;

call sp_agregarRegistro_sueño('2025-01-05', 7.00, 'Bien',      1);
call sp_agregarRegistro_sueño('2025-01-06', 5.50, 'Regular',   2);
call sp_agregarRegistro_sueño('2025-01-07', 8.00, 'Excelente', 3);
call sp_agregarRegistro_sueño('2025-01-08', 4.00, 'Mal',       4);
call sp_agregarRegistro_sueño('2025-01-09', 6.50, 'Bien',      5);
call sp_agregarRegistro_sueño('2025-01-10', 9.00, 'Excelente', 6);
call sp_agregarRegistro_sueño('2025-01-11', 3.50, 'Mal',       7);
call sp_agregarRegistro_sueño('2025-01-12', 7.50, 'Bien',      8);
call sp_agregarRegistro_sueño('2025-01-13', 6.00, 'Regular',   9);
call sp_agregarRegistro_sueño('2025-01-14', 8.00, 'Excelente', 10);

-- Editar registro_sueno
delimiter $$
create procedure sp_editarRegistro_sueño(
    in p_id_registro_sueño int,
    in p_fecha_sueño date,
    in p_horas_dormidas decimal(4,2),
    in p_calidad_sueño varchar(100),
    in p_fk_id_usuario int
)
begin
    update Registro_sueño
    set fecha_sueño = p_fecha_sueño, horas_dormidas = p_horas_dormidas, calidad_sueño = p_calidad_sueño, fk_id_usuario = p_fk_id_usuario
    where id_registro_sueño=p_id_registro_sueño;
end $$
delimiter ;

-- ======================== objetivo_meditacion ======================== --

delimiter $$
create procedure sp_listarObjetivo_meditacion()
begin
    select * from Objetivo_meditacion order by id_objetivo_meditacion;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarObjetivo_meditacion(
    in p_tiempo_objetivo varchar(50),
    in p_dias_objetivo int,
    in p_fk_id_usuario int
)
begin
    insert into Objetivo_meditacion(tiempo_objetivo,dias_objetivo,fk_id_usuario)
    values(p_tiempo_objetivo,p_dias_objetivo,p_fk_id_usuario);
end $$
delimiter ;

call sp_agregarObjetivo_meditacion('2 semanas', 7,  1);
call sp_agregarObjetivo_meditacion('4 semanas', 3,  2);
call sp_agregarObjetivo_meditacion('6 semanas', 5,  3);
call sp_agregarObjetivo_meditacion('8 semanas', 2,  4);
call sp_agregarObjetivo_meditacion('1 mes',     4,  5);
call sp_agregarObjetivo_meditacion('3 meses',   6,  6);
call sp_agregarObjetivo_meditacion('2 semanas', 7,  7);
call sp_agregarObjetivo_meditacion('4 semanas', 3,  8);
call sp_agregarObjetivo_meditacion('6 semanas', 5,  9);
call sp_agregarObjetivo_meditacion('8 semanas', 2,  10);

delimiter $$
create procedure sp_editarObjetivo_meditacion(
    in p_id_objetivo_meditacion int,
    in p_tiempo_objetivo varchar(50),
    in p_dias_objetivo int,
    in p_fk_id_usuario int
)
begin
    update Objetivo_meditacion set tiempo_objetivo = p_tiempo_objetivo, dias_objetivo = p_dias_objetivo, fk_id_usuario = p_fk_id_usuario
    where id_objetivo_meditacion = p_id_objetivo_meditacion;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarObjetivo_meditacion(
    in p_id_objetivo_meditacion int
)
begin
    delete from Objetivo_meditacion where id_objetivo_meditacion=p_id_objetivo_meditacion;
end $$
delimiter ;


-- ========================= entrada_diario ===================== --
delimiter $$
create procedure sp_listarEntrada_diario()
begin
    select * from Entrada_diario order by id_entrada_diario;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarEntrada_diario(
    in p_fecha date,
    in p_que_paso text,
    in p_plan_mañana text,
    in p_reflexion text,
    in p_fk_id_usuario int
)
begin
    insert into Entrada_diario(fecha,que_paso,plan_mañana,reflexion,fk_id_usuario)
    values(p_fecha,p_que_paso,p_plan_mañana,p_reflexion,p_fk_id_usuario);
end $$
delimiter ;

call sp_agregarEntrada_diario('2025-01-01', 'Empecé el año con energía y optimismo.',          'Meditar 10 minutos al despertar.',     'Pequeños pasos hacen grandes cambios.',  1);
call sp_agregarEntrada_diario('2025-01-02', 'Tuve una reunión difícil en el trabajo.',         'Preparar mejor mis presentaciones.',   'Aprender de los errores es crecer.',     2);
call sp_agregarEntrada_diario('2025-01-03', 'Salí a correr por primera vez en meses.',         'Repetir el ejercicio mañana.',         'El cuerpo agradece el movimiento.',      3);
call sp_agregarEntrada_diario('2025-01-04', 'Pasé tiempo de calidad con mi familia.',          'Llamar a un amigo que extraño.',       'Las relaciones son lo más valioso.',     4);
call sp_agregarEntrada_diario('2025-01-05', 'Leí 30 páginas de Hábitos Atómicos.',             'Continuar leyendo cada noche.',        'Leer transforma la mente.',              5);
call sp_agregarEntrada_diario('2025-01-06', 'Me costó concentrarme por el cansancio.',         'Dormir más temprano esta noche.',      'El descanso también es productividad.',  6);
call sp_agregarEntrada_diario('2025-01-07', 'Completé todos mis objetivos del día.',           'Mantener esta constancia.',            'La disciplina supera al talento.',       7);
call sp_agregarEntrada_diario('2025-01-08', 'Cociné saludable por primera vez en la semana.', 'Planear el menú de la semana.',        'Cuidar la alimentación es amor propio.', 8);
call sp_agregarEntrada_diario('2025-01-09', 'Sentí ansiedad por las responsabilidades.',      'Practicar respiración consciente.',    'Aceptar las emociones sin juicio.',      9);
call sp_agregarEntrada_diario('2025-01-10', 'Ayudé a un compañero en un proyecto.',           'Seguir siendo generoso con mi tiempo.','El servicio a otros da sentido.',        10);

delimiter $$
create procedure sp_editarEntrada_diario(
    in p_id_entrada_diario int,
    in p_fecha date,
    in p_que_paso text,
    in p_plan_mañana text,
    in p_reflexion text,
    in p_fk_id_usuario int
)
begin
    update Entrada_diario
    set fecha = p_fecha, que_paso = p_que_paso, plan_mañana = p_plan_mañana, reflexion = p_reflexion
    where id_entrada_diario = p_id_entrada_diario;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarEntrada_diario(
    in p_id_entrada_diario int
)
begin
    delete from Entrada_diario where id_entrada_diario=p_id_entrada_diario;
end $$
delimiter ;

delimiter $$
create procedure sp_buscar_entrada_diario(
    in p_id_entrada_diario int
)
begin
    select * from entrada_diario where id_entrada_diario = p_id_entrada_diario;
end $$
delimiter ;


-- ==================== registro_meditacion =================== --
delimiter $$
create procedure sp_listarRegistro_meditacion()
begin
    select * from Registro_meditacion order by id_registro_meditacion;
end $$
delimiter ;

delimiter $$
create procedure sp_agregarRegistro_meditacion(
    in p_tipo_meditacion varchar(20),
    in p_duracion_minutos int,
    in p_nivel_dificultad varchar(20),
    in p_fk_id_usuario int
)
begin
    insert into Registro_meditacion(tipo_meditacion,duracion_minutos,nivel_dificultad,fk_id_usuario)
    values(p_tipo_meditacion,p_duracion_minutos,p_nivel_dificultad,p_fk_id_usuario);
end $$
delimiter ;

call sp_agregarRegistro_meditacion('Guiada',      15, 'Principiante', 1);
call sp_agregarRegistro_meditacion('Respiracion', 10, 'Principiante', 2);
call sp_agregarRegistro_meditacion('Mindfulness', 20, 'Intermedio',   3);
call sp_agregarRegistro_meditacion('BodyScan',    30, 'Avanzado',     4);
call sp_agregarRegistro_meditacion('Guiada',      25, 'Intermedio',   5);
call sp_agregarRegistro_meditacion('Respiracion', 15, 'Principiante', 6);
call sp_agregarRegistro_meditacion('Mindfulness', 10, 'Principiante', 7);
call sp_agregarRegistro_meditacion('BodyScan',    20, 'Intermedio',   8);
call sp_agregarRegistro_meditacion('Guiada',      30, 'Avanzado',     9);
call sp_agregarRegistro_meditacion('Respiracion', 10, 'Principiante', 10);

delimiter $$
create procedure sp_editarRegistro_meditacion(
    in p_id_registro_meditacion int,
    in p_tipo_meditacion varchar(20),
    in p_duracion_minutos int,
    in p_nivel_dificultad varchar(20),
    in p_fk_id_usuario int
)
begin
    update Registro_meditacion
    set tipo_meditacion = p_tipo_meditacion, duracion_minutos = p_duracion_minutos, nivel_dificultad = p_nivel_dificultad, fk_id_usuario = p_fk_id_usuario
    where id_registro_meditacion = p_id_registro_meditacion;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarRegistro_meditacion(
    in p_id_registro_meditacion int
)
begin
    delete from Registro_meditacion where id_registro_meditacion=p_id_registro_meditacion;
end $$
delimiter ;


-- ==================================================== FUNCIONES ======================================================== --
delimiter $$
create function fn_promedio_sueño(p_id_usuario int)
returns decimal(4,2)
deterministic
begin
    declare promedio decimal(4,2);

    select avg(horas_dormidas)
    into promedio
    from Registro_sueño
    where fk_id_usuario=p_id_usuario;

    return ifnull(promedio,0);
end $$
delimiter ;

-- ===================================================== TRIGGERS ========================================================= --

-- ============ racha_ejercicio ============ --
delimiter $$
create trigger trg_validar_racha_ejercicio
before insert on racha_ejercicio
for each row
begin
    declare ultima_fecha    date;
    declare dias_actual     int;

    if new.dias_consecutivos < 0 then
        signal sqlstate '45000' set message_text = 'Los dias consecutivos no pueden ser negativos';
    end if;

    select fecha, dias_consecutivos
    into ultima_fecha, dias_actual
    from racha_ejercicio
    where fk_id_usuario = new.fk_id_usuario
    order by fecha desc
    limit 1;

    if ultima_fecha = new.fecha then
        signal sqlstate '45000' set message_text = 'Ya existe una racha registrada para hoy';
    end if;

    if ultima_fecha = date_sub(new.fecha, interval 1 day) then
        set new.dias_consecutivos = dias_actual + 1;
    else
        set new.dias_consecutivos = 1;
    end if;
end $$
delimiter ;


-- ===================== racha_lectura ===================== --
delimiter $$
create trigger trg_validar_racha_lectura
before insert on racha_lectura
for each row
begin
    declare ultima_fecha    date;
    declare dias_actual     int;

    if new.dias_consecutivos < 0 then
        signal sqlstate '45000' set message_text = 'Los dias consecutivos no pueden ser negativos';
    end if;

    select fecha, dias_consecutivos
    into ultima_fecha, dias_actual
    from racha_lectura
    where fk_id_usuario = new.fk_id_usuario
    order by fecha desc
    limit 1;

    if ultima_fecha = new.fecha then
        signal sqlstate '45000' set message_text = 'Ya existe una racha registrada para hoy';
    end if;

    if ultima_fecha = date_sub(new.fecha, interval 1 day) then
        set new.dias_consecutivos = dias_actual + 1;
    else
        set new.dias_consecutivos = 1;
    end if;
end $$
delimiter ;


-- ====================== estado objetivo ======================= --
delimiter $$
create trigger trg_validar_estado_objetivo_update
before update on objetivos
for each row
begin
    declare promedio_horas decimal(4,2);

    set promedio_horas = fn_promedio_sueno(new.fk_id_usuario);

    if new.fecha_objetivo < curdate() then
        set new.estado_objetivo = 'vencido';
    elseif promedio_horas >= 8 then
        set new.estado_objetivo = 'cumplido';
    else
        set new.estado_objetivo = 'pendiente';
    end if;
end $$
delimiter ;