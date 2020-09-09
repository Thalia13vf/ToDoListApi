create table tarefa(
	id bigint not null auto_increment,
	titulo varchar(120) not null,
	descricao text not null,
	data datetime,
	
	primary key(id)
);