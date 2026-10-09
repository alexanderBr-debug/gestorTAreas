CREATE TABLE tb_tarea (

    id BIGSERIAL, 
    titulo VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    completada BOOLEAN DEFAULT FALSE,
    fecha_limite DATE,
    user_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_tarea_usuario FOREIGN KEY (user_id) REFERENCES tb_users(id)
);