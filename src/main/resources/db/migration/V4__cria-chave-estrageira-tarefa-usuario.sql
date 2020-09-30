alter table tarefa 
add constraint fk_tarefa_usuario 
foreign key (usuario_id) references usuario (id);