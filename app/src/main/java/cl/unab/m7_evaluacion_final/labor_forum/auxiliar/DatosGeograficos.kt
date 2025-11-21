package cl.unab.m7_evaluacion_final.labor_forum.auxiliar

object DatosGeograficos {

    val regiones = listOf(
        "Región de Arica y Parinacota",
        "Región de Tarapacá",
        "Región de Antofagasta",
        "Región de Atacama",
        "Región de Coquimbo",
        "Región de Valparaíso",
        "Región Metropolitana de Santiago",
        "Región de O'Higgins",
        "Región del Maule",
        "Región de Ñuble",
        "Región del Biobío",
        "Región de La Araucanía",
        "Región de Los Ríos",
        "Región de Los Lagos",
        "Región de Aysén",
        "Región de Magallanes"
    )

    fun obtenerComunasPorRegion(region: String): List<String> {
        return when (region) {
            "Región de Arica y Parinacota" -> listOf("Arica", "Camarones", "Putre", "General Lagos")
            "Región de Tarapacá" -> listOf("Alto Hospicio", "Camiña", "Colchane", "Huara", "Iquique",
                "Pica", "Pozo Almonte")
            "Región de Antofagasta" -> listOf("Antofagasta", "Calama", "María Elena", "Mejillones",
                "Ollagüe", "San Pedro de Atacama", "Sierra Gorda", "Taltal", "Tocopilla")
            "Región de Atacama" -> listOf("Alto del Carmen", "Caldera", "Chañaral", "Copiapó",
                "Diego de Almagro", "Freirina", "Huasco", "Tierra Amarilla", "Vallenar")
            "Región de Coquimbo" -> listOf("Andacollo", "Canela", "Combarbalá", "Coquimbo", "Illapel",
                "La Higuera", "La Serena", "Los Vilos", "Monte Patria", "Ovalle", "Paihuano", "Punitaqui",
                "Río Hurtado", "Salamanca", "Vicuña")
            "Región de Valparaíso" -> listOf("Algarrobo", "Cabildo", "Calera", "Calle Larga", "Cartagena",
                "Casablanca", "Catemu", "Concón", "El Quisco", "El Tabo", "Hijuelas", "Isla de Pascua",
                "Juan Fernández", "La Cruz", "La Ligua", "Limache", "Llaillay", "Los Andes", "Nogales",
                "Olmué", "Panquehue", "Papudo", "Petorca", "Puchuncaví", "Putaendo", "Quillota", "Quilpué",
                "Quintero", "Rinconada", "San Antonio", "San Esteban", "San Felipe", "Santa María",
                "Santo Domingo", "Valparaíso", "Villa Alemana", "Viña del Mar", "Zapallar")
            "Región Metropolitana de Santiago" -> listOf("Alhué", "Buin", "Calera de Tango", "Cerrillos",
                "Cerro Navia", "Colina", "Conchalí", "Curacaví", "El Bosque", "El Monte", "Estación Central",
                "Huechuraba", "Independencia", "Isla de Maipo", "La Cisterna", "La Florida", "La Granja",
                "La Pintana", "La Reina", "Lampa", "Las Condes", "Lo Barnechea", "Lo Espejo", "Lo Prado",
                "Macul", "Maipú", "María Pinto", "Melipilla", "Ñuñoa", "Padre Hurtado", "Paine",
                "Pedro Aguirre Cerda", "Peñaflor", "Peñalolén", "Pirque", "Providencia", "Pudahuel",
                "Puente Alto", "Quilicura", "Quinta Normal", "Recoleta", "Renca", "San Bernardo",
                "San Joaquín", "San José de Maipo", "San Miguel", "San Pedro", "San Ramón", "Santiago",
                "Talagante", "Tiltil", "Vitacura")
            "Región de O'Higgins" -> listOf("Chépica", "Chimbarongo",
                "Codegua", "Coinco", "Coltauco", "Doñihue", "Graneros", "La Estrella", "Las Cabras",
                "Litueche", "Lolol", "Machalí", "Malloa", "Marchihue", "Mostazal", "Nancagua", "Navidad",
                "Olivar", "Palmilla", "Paredones", "Peralillo", "Peumo", "Pichidegua", "Pichilemu",
                "Placilla", "Pumanque", "Quinta de Tilcoco", "Rancagua", "Rengo", "Requínoa",
                "San Fernando", "San Vicente")
            "Región del Maule" -> listOf("Cauquenes", "Chanco", "Colbún", "Constitución", "Curepto",
                "Curicó", "Empedrado", "Hualañé", "Licantén", "Linares", "Longaví", "Maule", "Molina",
                "Parral", "Pelarco", "Pelluhue", "Pencahue", "Rauco", "Retiro", "Río Claro", "Romeral",
                "Sagrada Familia", "San Clemente", "San Javier", "San Rafael", "Talca", "Teno",
                "Vichuquén", "Villa Alegre", "Yerbas Buenas")
            "Región de Ñuble" -> listOf("Bulnes", "Chillán", "Chillán Viejo", "Cobquecura", "Coelemu",
                "Coihueco", "El Carmen", "Ninhue", "Ñiquén", "Pemuco", "Pinto", "Portezuelo", "Quillón",
                "Quirihue", "Ránquil", "San Carlos", "San Fabián", "San Ignacio", "San Nicolás",
                "Treguaco", "Yungay")
            "Región del Biobío" -> listOf("Alto Biobío", "Antuco", "Arauco", "Cabrero", "Cañete",
                "Chiguayante", "Concepción", "Contulmo", "Coronel", "Curanilahue", "Florida", "Hualpén",
                "Hualqui", "Laja", "Lebu", "Los Álamos", "Los Ángeles", "Lota", "Mulchén", "Nacimiento",
                "Negrete", "Penco", "Quilaco", "Quilleco", "San Pedro de la Paz", "San Rosendo",
                "Santa Bárbara", "Santa Juana", "Talcahuano", "Tirúa", "Tomé", "Tucapel", "Yumbel")
            "Región de La Araucanía" -> listOf("Angol", "Carahue", "Cholchol", "Collipulli", "Cunco",
                "Curacautín", "Curarrehue", "Ercilla", "Freire", "Galvarino", "Gorbea", "Lautaro",
                "Loncoche", "Lonquimay", "Los Sauces", "Lumaco", "Melipeuco", "Nueva Imperial",
                "Padre Las Casas", "Perquenco", "Pitrufquén", "Pucón", "Purén", "Renaico", "Saavedra",
                "Temuco", "Teodoro Schmidt", "Toltén", "Traiguén", "Victoria", "Vilcún", "Villarrica")
            "Región de Los Ríos" -> listOf("Corral", "Futrono", "La Unión", "Lago Ranco", "Lanco",
                "Los Lagos", "Máfil", "Mariquina", "Paillaco", "Panguipulli", "Río Bueno", "Valdivia")
            "Región de Los Lagos" -> listOf("Ancud", "Calbuco", "Castro", "Chaitén", "Chonchi",
                "Cochamó", "Curaco de Vélez", "Dalcahue", "Fresia", "Frutillar", "Futaleufú",
                "Hualaihué", "Llanquihue", "Los Muermos", "Maullín", "Osorno", "Palena", "Puerto Montt",
                "Puerto Octay", "Puerto Varas", "Puqueldón", "Purranque", "Puyehue", "Queilén",
                "Quellón", "Quemchi", "Quinchao", "Río Negro", "San Juan de la Costa", "San Pablo")
            "Región de Aysén" -> listOf("Aysén", "Cisnes",
                "Cochrane", "Chile Chico", "Guaitecas", "Lago Verde", "O'Higgins", "Río Ibáñez",
                "Tortel")
            "Región de Magallanes" -> listOf("Antártica", "Cabo de Hornos",
                "Laguna Blanca", "Natales", "Palmilla", "Porvenir", "Primavera", "Punta Arenas",
                "Río Verde", "San Gregorio", "Timaukel", "Torres del Paine")
            else -> emptyList()
        }
    }
}