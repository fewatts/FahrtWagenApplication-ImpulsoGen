CREATE TABLE IF NOT EXISTS Carros (
    id_carro INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    placa VARCHAR(10) UNIQUE NOT NULL,
    valor DECIMAL(10, 2),
    manutencao_em_dia BOOLEAN NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS Clientes (
    id_cliente INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Reservas (
    id_reserva INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    carro_id INT NOT NULL,
    cliente_id INT NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    valor DECIMAL(10, 2),
    confirmada BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (carro_id) REFERENCES Carros(id_carro),
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id_cliente)
);
