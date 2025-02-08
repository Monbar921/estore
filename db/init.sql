-- Create Positions Table
CREATE TABLE IF NOT EXISTS position_type (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL
);

-- Create Electronics Types Table
CREATE TABLE IF NOT EXISTS electro_type (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL
);

-- Create Stores Table
CREATE TABLE IF NOT EXISTS shop (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address TEXT
);

-- Create Purchase Types Table
CREATE TABLE IF NOT EXISTS purchase_type (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL
);

-- Create Employees Table
CREATE TABLE IF NOT EXISTS employee (
    id BIGSERIAL PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100),
    birth_date DATE NOT NULL,
    position_id BIGINT REFERENCES position_type(id),
    shop_id BIGINT REFERENCES shop(id),
    gender BOOLEAN NOT NULL
);

-- Create Electronics Table
CREATE TABLE IF NOT EXISTS electro_item (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    etype_id BIGINT REFERENCES electro_type(id),
    price DECIMAL(10,2) NOT NULL,
    count INT NOT NULL,
    archive BOOLEAN NOT NULL,
    description TEXT
);

-- Create Purchases Table
CREATE TABLE IF NOT EXISTS purchase (
    id BIGSERIAL PRIMARY KEY,
    electro_id BIGINT REFERENCES electro_item(id),
    employee_id BIGINT REFERENCES employee(id),
    shop_id BIGINT REFERENCES shop(id),
    purchase_date date,
    type_id BIGINT REFERENCES purchase_type(id)
);

-- Create Many-to-Many Relationship Tables
CREATE TABLE IF NOT EXISTS electro_employee (
    electro_type_id BIGINT REFERENCES electro_type(id),
    employee_id BIGINT REFERENCES employee(id),
    PRIMARY KEY (electro_type_id, employee_id)
);

CREATE TABLE IF NOT EXISTS electro_shop (
    electro_item_id BIGINT REFERENCES electro_item(id),
    shop_id BIGINT REFERENCES shop(id),
    count INT NOT NULL,
    PRIMARY KEY (electro_item_id, shop_id)
);

-- Insert Initial Data
INSERT INTO positions (name) VALUES ('Manager'), ('Sales Associate') ON CONFLICT DO NOTHING;
INSERT INTO electronics_types (name) VALUES ('Laptop'), ('Smartphone'), ('Tablet') ON CONFLICT DO NOTHING;
INSERT INTO stores (name, address) VALUES ('Tech Store', '123 Main St'), ('Gadget Hub', '456 Market St') ON CONFLICT DO NOTHING;
INSERT INTO purchase_types (name) VALUES ('Online'), ('In-Store') ON CONFLICT DO NOTHING;