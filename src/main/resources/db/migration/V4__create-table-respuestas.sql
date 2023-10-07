create table respuestas (

        solucion bit,
        autor_id bigint,
        fecha_creacion datetime(6),
        id bigint not null auto_increment,
        topico_id bigint,
        mensaje text,

        primary key (id),

        constraint fk_respuestas_autor_id foreign key(autor_id) references usuarios(id),
        constraint fk_respuestas_topico_id foreign key(topico_id) references topicos(id)

) engine=InnoDB