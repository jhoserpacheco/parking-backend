
CREATE TABLE emails (
                        id uuid DEFAULT gen_random_uuid() NOT NULL,
                        from_email varchar(255) NOT NULL,
                        to_email varchar(255) NOT NULL,
                        subject varchar(255) NOT NULL,
                        body text NULL,
                        status varchar(50) NOT NULL,
                        created_at timestamp NOT NULL,
                        CONSTRAINT emails_pkey PRIMARY KEY (id)
);

CREATE TABLE email_parking (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               from_email VARCHAR(255) NOT NULL,
                               to_email VARCHAR(255) NOT NULL,
                               subject VARCHAR(255) NOT NULL,
                               text TEXT NOT NULL,
                               vehicle_plate VARCHAR(50) NOT NULL,
                               parking_name VARCHAR(100) NOT NULL,
                               status VARCHAR(50) NOT NULL
);
