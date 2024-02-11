CREATE TABLE clientes (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome varchar NULL,
	nomefantasia varchar NULL,
	razaosocial varchar NOT NULL,
	cnpj varchar NOT NULL
);

CREATE TABLE produtos (
	id INT AUTO_INCREMENT PRIMARY KEY,
	descricao varchar NOT NULL,
	estoque numeric NOT NULL,
	preco float NOT NULL,
	unidade varchar NOT NULL,
	ultimaatualizacao timestamp NOT NULL
);

CREATE TABLE status (
	id INT AUTO_INCREMENT PRIMARY KEY,
	descricao varchar NOT NULL
);

INSERT INTO public.status (id, descricao) VALUES(1, 'DIGITANDO');
INSERT INTO public.status (id, descricao) VALUES(2, 'FINALIZADO PARCIAL');
INSERT INTO public.status (id, descricao) VALUES(3, 'FINALIZADO');

CREATE TABLE venda (
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_cliente int NOT NULL,
	id_status int NOT NULL,
	valortotal float NOT NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL
);

ALTER TABLE venda ADD CONSTRAINT venda_clientes_fk FOREIGN KEY (id_cliente) REFERENCES clientes(id);
ALTER TABLE venda ADD CONSTRAINT venda_status_fk FOREIGN KEY (id_status) REFERENCES status(id);


CREATE TABLE vendaproduto (
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_venda int NOT NULL,
	id_produto int NOT NULL,
	valorprodutonavenda float NOT NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	quantidade int NOT NULL
);

ALTER TABLE vendaproduto ADD CONSTRAINT vendaproduto_produtos_fk FOREIGN KEY (id_produto) REFERENCES produtos(id);
ALTER TABLE vendaproduto ADD CONSTRAINT vendaproduto_venda_fk FOREIGN KEY (id_venda) REFERENCES venda(id);


CREATE TABLE vendaprodutoerrofinalizacao (
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_vendaproduto int4 NULL,
	motivoerro varchar NULL
);

ALTER TABLE vendaprodutoerrofinalizacao ADD CONSTRAINT vendaprodutoerrofinalizacao_vendaproduto_fk FOREIGN KEY (id_vendaproduto) REFERENCES vendaproduto(id);