CREATE TABLE public.clientes (
	id serial4 NOT NULL,
	nome varchar NULL,
	nomefantasia varchar NULL,
	razaosocial varchar NOT NULL,
	cnpj varchar NOT NULL,
	CONSTRAINT clientes_pk PRIMARY KEY (id)
);

CREATE TABLE public.produtos (
	id serial4 NOT NULL,
	descricao varchar NOT NULL,
	estoque numeric NOT NULL,
	preco float8 NOT NULL,
	unidade varchar NOT NULL,
	ultimaatualizacao timestamp NOT NULL,
	CONSTRAINT produtos_pk PRIMARY KEY (id)
);

CREATE TABLE public.status (
	id serial4 NOT NULL,
	descricao varchar NOT NULL,
	CONSTRAINT status_pk PRIMARY KEY (id)
);

CREATE TABLE public.venda (
	id serial4 NOT NULL,
	id_cliente int4 NOT NULL,
	id_status int4 NOT NULL,
	valortotal float8 NOT NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	CONSTRAINT venda_pk PRIMARY KEY (id)
);

ALTER TABLE public.venda ADD CONSTRAINT venda_clientes_fk FOREIGN KEY (id_cliente) REFERENCES public.clientes(id);
ALTER TABLE public.venda ADD CONSTRAINT venda_status_fk FOREIGN KEY (id_status) REFERENCES public.status(id);


CREATE TABLE public.vendaproduto (
	id serial4 NOT NULL,
	id_venda int4 NOT NULL,
	id_produto int4 NOT NULL,
	valorprodutonavenda float8 NOT NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	quantidade int8 NOT NULL,
	CONSTRAINT vendaproduto_pk PRIMARY KEY (id)
);

ALTER TABLE public.vendaproduto ADD CONSTRAINT vendaproduto_produtos_fk FOREIGN KEY (id_produto) REFERENCES public.produtos(id);
ALTER TABLE public.vendaproduto ADD CONSTRAINT vendaproduto_venda_fk FOREIGN KEY (id_venda) REFERENCES public.venda(id);


CREATE TABLE public.vendaprodutoerrofinalizacao (
	id serial4 NOT NULL,
	id_vendaproduto int4 NULL,
	motivoerro varchar NULL,
	CONSTRAINT vendaprodutoerrofinalizacao_pk PRIMARY KEY (id)
);

ALTER TABLE public.vendaprodutoerrofinalizacao ADD CONSTRAINT vendaprodutoerrofinalizacao_vendaproduto_fk FOREIGN KEY (id_vendaproduto) REFERENCES public.vendaproduto(id);