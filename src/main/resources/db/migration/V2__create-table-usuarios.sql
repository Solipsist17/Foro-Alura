create table usuarios (

        id bigint not null auto_increment,
        contraseña varchar(255),
        email varchar(255),
        nombre varchar(255),

        primary key (id)

) engine=InnoDB