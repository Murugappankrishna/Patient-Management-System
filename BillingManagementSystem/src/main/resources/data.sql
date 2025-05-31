-- Create the 'account' table without a foreign key constraint
CREATE TABLE IF NOT EXISTS account (
                                       account_id UUID PRIMARY KEY,
                                       patient_name VARCHAR(255) NOT NULL,
    patient_email VARCHAR(255) UNIQUE NOT NULL,
    patient_id UUID,
    payment_status VARCHAR(50)
    );

-- Insert sample accounts referencing patient IDs (logically, not enforced by FK)
INSERT INTO account (account_id, patient_name, patient_email, patient_id, payment_status)
SELECT 'a23e4567-e89b-12d3-a456-426614174000',
       'John Doe',
       'john.doe@example.com',
       '123e4567-e89b-12d3-a456-426614174000',
       'NEW'
    WHERE NOT EXISTS (
    SELECT 1 FROM account WHERE account_id = 'a23e4567-e89b-12d3-a456-426614174000'
);

INSERT INTO account (account_id, patient_name, patient_email, patient_id, payment_status)
SELECT 'a23e4567-e89b-12d3-a456-426614174001',
       'Jane Smith',
       'jane.smith@example.com',
       '123e4567-e89b-12d3-a456-426614174001',
       'PENDING'
    WHERE NOT EXISTS (
    SELECT 1 FROM account WHERE account_id = 'a23e4567-e89b-12d3-a456-426614174001'
);

INSERT INTO account (account_id, patient_name, patient_email, patient_id, payment_status)
SELECT 'a23e4567-e89b-12d3-a456-426614174002',
       'Alice Johnson',
       'alice.johnson@example.com',
       '123e4567-e89b-12d3-a456-426614174002',
       'COMPLETED'
    WHERE NOT EXISTS (
    SELECT 1 FROM account WHERE account_id = 'a23e4567-e89b-12d3-a456-426614174002'
);

INSERT INTO account (account_id, patient_name, patient_email, patient_id, payment_status)
SELECT 'a23e4567-e89b-12d3-a456-426614174003',
       'Bob Brown',
       'bob.brown@example.com',
       '123e4567-e89b-12d3-a456-426614174003',
       'FAILED'
    WHERE NOT EXISTS (
    SELECT 1 FROM account WHERE account_id = 'a23e4567-e89b-12d3-a456-426614174003'
);