import { useState } from 'react';
import { Link } from 'react-router-dom';

function Login() {
 // Guardamos lo que el usuario escribe
  const [credenciales, setCredenciales] = useState({
    email: '',
    password: ''
  });

 //Función que se ejecuta cada vez que el usuario toca una tecla
  const handleChange = (e) => {
    setCredenciales({
      ...credenciales,
      [e.target.name]: e.target.value
    });
  };

 //Función que se ejecuta al apretar el botón "Ingresar"
  const handleSubmit = (e) => {
    e.preventDefault(); // Evitamos que la página se recargue en blanco
    console.log("Intentando iniciar sesión con:", credenciales);
  };

  return (
    <div className="container d-flex justify-content-center align-items-center pb-5" style={{ minHeight: '100vh' }}>
      <div className="row">
        <div className="col">
          <div className="card shadow" style={{ width: '25rem' }}>
            <div className="card-body">
              <h5 className="card-title text-center mb-4">Sistema Bancario</h5>
              <h6 className="card-subtitle mb-4 text-muted text-center">
                Iniciar Sesión
              </h6>
              
              {/* Le avisamos al formulario que ejecute handleSubmit */}
              <form onSubmit={handleSubmit}>
                
                {/* Campo Email */}
                <div className="mb-3">
                  <label htmlFor="inputEmail" className="form-label">Email</label>
                  <input 
                    type="email" 
                    className="form-control" 
                    id="inputEmail"
                    name="email"
                    placeholder="ejemplo@correo.com" 
                    value={credenciales.email} 
                    onChange={handleChange} 
                  />
                </div>

                {/* Campo Contraseña */}
                <div className="mb-4">
                  <label htmlFor="inputPassword" className="form-label">Contraseña</label>
                  <input 
                    type="password" 
                    className="form-control" 
                    id="inputPassword"
                    name="password"
                    placeholder="********" 
                    value={credenciales.password}
                    onChange={handleChange}
                  />
                </div>

                {/* Botón Ingresar */}
                <div className="d-grid mb-3">
                  <button type="submit" className="btn btn-primary">
                    Ingresar
                  </button>
                </div>

                {/* Enlace al Registro */}
                <div className="text-center mt-3">
                  <span>¿No tenés una cuenta? </span>
                  <Link to="/registro" className="text-decoration-none" style={{ fontWeight: '500' }}>
                    Registrate acá
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

export default Login;