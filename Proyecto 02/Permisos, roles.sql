/* 1. Roles-grupo (NOLOGIN) */
CREATE ROLE emp   NOLOGIN;             -- Perfil de empleado
CREATE ROLE admin NOLOGIN IN ROLE emp; -- Perfil de admin 


/* 2. Rol dueño (NOLOGIN) */
CREATE ROLE video NOLOGIN;

/* 3. Usuarios con sus respectivas contraseñas */
CREATE ROLE empleado1     LOGIN PASSWORD '12345';
CREATE ROLE administrador1 LOGIN PASSWORD '54321';

GRANT emp   TO empleado1;
GRANT admin TO administrador1;

/* ───────────────────────────────────────────────
   4. Permisos sobre funciones y procedimientos
   ─────────────────────────────────────────────── */
ALTER FUNCTION  public.fn_insert_customer (
      INT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, INT, TEXT, TEXT
) OWNER TO video;
ALTER FUNCTION  public.fn_insert_customer (
      INT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, INT, TEXT, TEXT
) SECURITY DEFINER;

ALTER FUNCTION  public.fn_register_rental (INT, INT, INT)
    OWNER TO video;
ALTER FUNCTION  public.fn_register_rental (INT, INT, INT)
    SECURITY DEFINER;

ALTER PROCEDURE public.sp_register_return (INT, INT, NUMERIC)
    OWNER TO video;
ALTER PROCEDURE public.sp_register_return (INT, INT, NUMERIC)
    SECURITY DEFINER;

ALTER FUNCTION  public.fn_search_film (TEXT)
    OWNER TO video;
ALTER FUNCTION  public.fn_search_film (TEXT)
    SECURITY DEFINER;

-- Dar permiso de solo ejecución 
GRANT EXECUTE ON FUNCTION  public.fn_register_rental (INT, INT, INT) TO emp;
GRANT EXECUTE ON PROCEDURE public.sp_register_return (INT, INT, NUMERIC) TO emp;
GRANT EXECUTE ON FUNCTION  public.fn_search_film (TEXT) TO emp;

-- Dar permiso al rol admin de ejecución
GRANT EXECUTE ON FUNCTION public.fn_insert_customer (
      INT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, INT, TEXT, TEXT
) TO admin;

-- Remover los permisos que postgresql brinda por defecto
REVOKE EXECUTE ON FUNCTION  public.fn_insert_customer  (
        INT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, INT, TEXT, TEXT
) FROM PUBLIC;
REVOKE EXECUTE ON FUNCTION  public.fn_register_rental (INT, INT, INT) FROM PUBLIC;
REVOKE EXECUTE ON PROCEDURE public.sp_register_return (INT, INT, NUMERIC) FROM PUBLIC;
REVOKE EXECUTE ON FUNCTION  public.fn_search_film (TEXT) FROM PUBLIC;

ALTER DEFAULT PRIVILEGES FOR ROLE video
    REVOKE ALL ON TABLES FROM emp, admin;


ALTER DEFAULT PRIVILEGES FOR ROLE video
    REVOKE ALL ON SEQUENCES FROM emp, admin;

ALTER DEFAULT PRIVILEGES FOR ROLE video
    REVOKE ALL ON ROUTINES FROM emp, admin;




