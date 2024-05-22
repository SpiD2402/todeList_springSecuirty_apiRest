package com.example.todelist.contants;

public class Const {
    public static final int OK = 200;
    public static final String OK_MESSAGE = "Solicitud exitosa";

    public static final int CREATED = 201;
    public static final String CREATED_MESSAGE = "Recurso creado exitosamente";

    public static final int ACCEPTED = 202;
    public static final String ACCEPTED_MESSAGE = "Solicitud aceptada para procesamiento";

    public static final int NO_CONTENT = 204;
    public static final String NO_CONTENT_MESSAGE = "Solicitud exitosa, sin contenido en la respuesta";

    // Códigos de redirección
    public static final int MOVED_PERMANENTLY = 301;
    public static final String MOVED_PERMANENTLY_MESSAGE = "Recurso movido permanentemente";

    public static final int TEMPORARY_REDIRECT = 307;
    public static final String TEMPORARY_REDIRECT_MESSAGE = "Redirección temporal";

    // Códigos de error del cliente
    public static final int BAD_REQUEST = 400;
    public static final String BAD_REQUEST_MESSAGE = "Solicitud incorrecta";

    public static final int UNAUTHORIZED = 401;
    public static final String UNAUTHORIZED_MESSAGE = "No autorizado";

    public static final int FORBIDDEN = 403;
    public static final String FORBIDDEN_MESSAGE = "Prohibido, no tiene permiso";

    public static final int NOT_FOUND = 404;
    public static final String NOT_FOUND_MESSAGE = "Recurso no encontrado";

    public static final int METHOD_NOT_ALLOWED = 405;
    public static final String METHOD_NOT_ALLOWED_MESSAGE = "Método no permitido";

    public static final int CONFLICT = 409;
    public static final String CONFLICT_MESSAGE = "Conflicto con el estado actual";

    // Códigos de error del servidor
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Error interno del servidor";

    public static final int NOT_IMPLEMENTED = 501;
    public static final String NOT_IMPLEMENTED_MESSAGE = "No implementado";

    public static final int SERVICE_UNAVAILABLE = 503;
    public static final String SERVICE_UNAVAILABLE_MESSAGE = "Servicio no disponible";
}
