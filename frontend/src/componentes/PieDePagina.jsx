import { Link } from "react-router-dom";

export const PieDePagina = () => {
    const obtenerAnioActual = () => {
        return new Date().getFullYear();
    };

    return (
        // Cambiamos bg-light por un estilo personalizado de azul oscuro
        <footer
            className="text-white pt-5 pb-4 mt-5"
            style={{ backgroundColor: "#0a192f", borderTop: "3px solid #0d6efd" }}
        >
            <div className="container">
                <div className="row text-center text-md-start">

                    {/* Columna 1: Marca */}
                    <div className="col-md-4 col-lg-4 col-xl-3 mx-auto mt-3">
                        <h5 className="text-uppercase mb-4 fw-bold" style={{ color: "#4facfe" }}>
                            🏦 Mi Banco
                        </h5>
                        <p className="text-white-50">
                            Tu banco digital de confianza. Gestioná tus finanzas con la seguridad y tecnología que te merecés.
                        </p>
                    </div>

                    {/* Columna 2: Enlaces - Cambiamos text-muted por text-white-50 */}
                    <div className="col-md-2 col-lg-2 col-xl-2 mx-auto mt-3">
                        <h5 className="text-uppercase mb-4 fw-bold">Navegación</h5>
                        <p><Link to="/dashboard" className="text-decoration-none text-white-50 hover-white">Inicio</Link></p>
                        <p><Link to="/crear-cuenta" className="text-decoration-none text-white-50 hover-white">Nueva Cuenta</Link></p>
                        <p><Link to="/transferencias" className="text-decoration-none text-white-50 hover-white">Transferencias</Link></p>
                    </div>

                    {/* Columna 3: Contacto */}
                    <div className="col-md-4 col-lg-3 col-xl-3 mx-auto mt-3">
                        <h5 className="text-uppercase mb-4 fw-bold">Soporte</h5>
                        <p className="text-white-50"><span className="me-2">📍</span> Corrientes, Argentina</p>
                        <p className="text-white-50"><span className="me-2">📧</span> contacto@mibanco.com.ar</p>
                        <p className="text-white-50"><span className="me-2">📞</span> 0800-999-BANCO</p>
                    </div>
                </div>


                {/* --- Línea divisoria --- */}
                <hr className="mb-4 mt-4 bg-white opacity-25" />

                <div className="row">
                    {/* Columna de ancho completo con texto centrado */}
                    <div className="col-12 text-center">
                        <p className="text-white-50 mb-2">
                            © {obtenerAnioActual()} Todos los derechos reservados:
                        </p>  
                    </div>
                </div>
            </div>
        </footer>
    );
};

export default PieDePagina;