create table usuario( 
	id bigint auto_increment not null,
    nome varchar(120) not null,
    email varchar(120) not null,
    senha varchar(120) not null,
    primary key(id)
);