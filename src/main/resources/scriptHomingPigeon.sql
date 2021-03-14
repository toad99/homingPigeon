CREATE TABLE public.account
(
    username character varying(32) COLLATE pg_catalog."default" NOT NULL,
    password character varying(128) COLLATE pg_catalog."default" NOT NULL,
    public_key character varying(255) COLLATE pg_catalog."default" NOT NULL,
    enable boolean NOT NULL DEFAULT true,
    CONSTRAINT account_pkey PRIMARY KEY (username)
);

CREATE TABLE public.conversation
(
    conversation_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT conversation_pkey PRIMARY KEY (conversation_id)
);

CREATE TABLE public.conversation_member
(
    member_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(32) COLLATE pg_catalog."default" NOT NULL,
    conversation_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT conversation_member_pkey PRIMARY KEY (member_id),
    CONSTRAINT fk_id_conv FOREIGN KEY (conversation_id)
        REFERENCES public.conversation (conversation_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_username FOREIGN KEY (username)
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.friend_request
(
    applicant character varying(32) COLLATE pg_catalog."default" NOT NULL,
    recipient character varying(32) COLLATE pg_catalog."default" NOT NULL,
    friend_request_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT friend_request_pkey PRIMARY KEY (friend_request_id),
    CONSTRAINT fk_applicant FOREIGN KEY (applicant)
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_recipient FOREIGN KEY (recipient)
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.friendship
(
    "user" character varying(32) COLLATE pg_catalog."default" NOT NULL,
    friend character varying(32) COLLATE pg_catalog."default" NOT NULL,
    friendship_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT friend_pkey PRIMARY KEY (friendship_id),
    CONSTRAINT fk_account_id FOREIGN KEY ("user")
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_friend FOREIGN KEY (friend)
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.message
(
    message_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    content text COLLATE pg_catalog."default" NOT NULL,
    recipient character varying(32) COLLATE pg_catalog."default" NOT NULL,
    sender character varying(32) COLLATE pg_catalog."default" NOT NULL,
    date timestamp without time zone NOT NULL,
    conversation_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT message_pkey PRIMARY KEY (message_id),
    CONSTRAINT fk_conversation_id FOREIGN KEY (conversation_id)
        REFERENCES public.conversation (conversation_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_recipient FOREIGN KEY (recipient)
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_sender FOREIGN KEY (message_id)
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);