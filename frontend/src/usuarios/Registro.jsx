import { Link } from "react-router-dom";

function Registro() {
  return (
    <div className="container d-flex justify-content-center align-items-center pb-5 pt-4" style={{ minHeight: '100vh' }}>
      <div className="row ">
        <div className="col">
          <div className="card shadow" style={{ width: '30rem' }}> {/* Tarjeta un poco más ancha */}
            <div className="card-body">
              <h5 className="card-title text-center mb-4">Sistema Bancario</h5>
              <h6 className="card-subtitle mb-4 text-muted text-center">
                Crear Nueva Cuenta
              </h6>
              
              <form>
                {/* Fila interna para Nombre y Apellido en la misma línea */}
                <div className="row mb-3">
                  <div className="col">
                    <label htmlFor="inputNombre" className="form-label">Nombre</label>
                    <input type="text" className="form-control" id="inputNombre" placeholder="Juan" />
                  </div>
                  <div className="col">
                    <label htmlFor="inputApellido" className="form-label">Apellido</label>
                    <input type="text" className="form-control" id="inputApellido" placeholder="Pérez" />
                  </div>
                </div>

                {/* DNI */}
                <div className="mb-3">
                  <label htmlFor="inputDni" className="form-label">DNI</label>
                  <input type="number" className="form-control" id="inputDni" placeholder="Sin puntos ni espacios" />
                </div>

                {/* Email */}
                <div className="mb-3">
                  <label htmlFor="inputEmail" className="form-label">Email</label>
                  <input type="email" className="form-control" id="inputEmail" placeholder="ejemplocorreo@gmail.com" />
                </div>

                {/* Contraseña */}
                <div className="mb-3">
                  <label htmlFor="inputPassword" className="form-label">Contraseña</label>
                  <input type="password" className="form-control" id="inputPassword" placeholder="********" />
                </div>

                {/* Repetir Contraseña */}
                <div className="mb-4">
                  <label htmlFor="inputConfirmPassword" className="form-label">Repetir Contraseña</label>
                  <input type="password" className="form-control" id="inputConfirmPassword" placeholder="********" />
                </div>

                {/* Botón de Registro */}
                <div className="d-grid mb-3">
                  <button type="submit" className="btn btn-primary">
                    Registrarme
                  </button>
                </div>

                {/* Volver al Login */}
                <div className="text-center mt-3">
                  <span>¿Ya tenés una cuenta? </span>
                  <Link to="/" className="text-decoration-none" style={{ fontWeight: '500' }}>
                    Iniciá sesión acá
                  </Link>
                </div>
              </form>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Registro;