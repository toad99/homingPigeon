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
    username character varying(32) COLLATE pg_catalog."default" NOT NULL,
    conversation_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
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
    friend1 character varying(32) COLLATE pg_catalog."default" NOT NULL,
    friend2 character varying(32) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT fk_friend1 FOREIGN KEY (friend1)
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_friend2 FOREIGN KEY (friend2)
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
    CONSTRAINT fk_sender FOREIGN KEY (sender)
        REFERENCES public.account (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE OR REPLACE VIEW public.friendship_transitive_view
 AS
 SELECT friendship.friend1,
    friendship.friend2
   FROM friendship
UNION
 SELECT friendship.friend2 AS friend1,
    friendship.friend1 AS friend2
   FROM friendship;

CREATE OR REPLACE FUNCTION fonction_insertFriendshipInsert() RETURNS TRIGGER AS $$
    BEGIN
        INSERT INTO friendship values (NEW.friend1,NEW.friend2);
    RETURN new;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_insertFriendshipInsert
    INSTEAD OF INSERT
    ON friendship_transitive_view
    FOR EACH ROW
    EXECUTE PROCEDURE fonction_insertFriendshipInsert();

CREATE OR REPLACE FUNCTION fonction_insertFriendshipDelete() RETURNS TRIGGER AS $$
    BEGIN
        DELETE FROM friendship where friend1=OLD.friend1 AND friend2=OLD.friend2;
    RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_insertFriendshipDelete
    INSTEAD OF DELETE
    ON friendship_transitive_view
    FOR EACH ROW
    EXECUTE PROCEDURE fonction_insertFriendshipDelete();