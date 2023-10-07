 create table topicos (

        autor_id bigint,
        curso_id bigint,
        fecha_creacion datetime(6),
        id bigint not null auto_increment,
        mensaje text,
        status enum ('CERRADO','NO_RESPONDIDO','NO_SOLUCIONADO','SOLUCIONADO'),
        titulo varchar(255),

        primary key (id),

        constraint fk_topicos_curso_id foreign key(curso_id) references cursos(id),
        constraint fk_topicos_autor_id foreign key(autor_id) references usuarios(id)

 ) engine=InnoDB