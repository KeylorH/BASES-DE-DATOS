package logica;

import jakarta.persistence.*;

@Entity
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "ReporteVentasProvincia",
            procedureName = "paquete_reportes_view.reporte_ventas_por_provincia",
            resultSetMappings = "VentasProvinciaMapping",
            parameters = {
                @StoredProcedureParameter(
                        name = "p_cursor",
                        mode = ParameterMode.REF_CURSOR,
                        type = void.class
                )
            }
    ),
    @NamedStoredProcedureQuery(
            name = "ReporteProductosBitacora",
            procedureName = "paquete_reportes_view.reporte_productos_y_bitacora",
            resultSetMappings = "ProductoBitacoraMapping",
            parameters = {
                @StoredProcedureParameter(
                        name = "p_cursor",
                        mode = ParameterMode.REF_CURSOR,
                        type = void.class)
            }
    )

})
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "VentasProvinciaMapping",
            classes = @ConstructorResult(
                    targetClass = VentasProvinciaDTO.class,
                    columns = {
                        @ColumnResult(name = "nombre_provincia", type = String.class),
                        @ColumnResult(name = "total_ventas", type = Double.class)
                    }
            )
    ),
    @SqlResultSetMapping(
            name = "ProductoBitacoraMapping",
            classes = @ConstructorResult(
                    targetClass = ProductoBitacoraDTO.class,
                    columns = {
                        @ColumnResult(name = "id_producto", type = Integer.class),
                        @ColumnResult(name = "nombre", type = String.class),
                        @ColumnResult(name = "categoria", type = String.class),
                        @ColumnResult(name = "fecha_cambio", type = java.sql.Date.class),
                        @ColumnResult(name = "precio_anterior", type = Double.class),
                        @ColumnResult(name = "precio_nuevo", type = Double.class)
                    }
            )
    )
})
public class ReportesSql {

    @Id
    private Long id;
}
