import { useState } from 'react';
import { Link } from 'react-router-dom';

function Registro() {
  // Guardamos lo que el usuario escribe
  const [datos, setDatos] = useState({
    nombre: '',
    apellido: '',
    dni: '',
    email: '',
    password: '',
    confirmPassword: ''
  });

  //Función que se ejecuta cada vez que el usuario toca una tecla
  const handleChange = (e) => {
    setDatos({
      ...datos, // Copia todo lo que ya estaba guardado
      [e.target.name]: e.target.value // Actualiza solo el campo que se está escribiendo
    });
  };

  //Función que se ejecuta al apretar el botón "Registrarme"
  const handleSubmit = (e) => {
    e.preventDefault(); // Evita que la página se recargue en blanco
    
    // Por ahora solo mostramos los datos en la consola para verificar que funciona
    console.log("Datos listos para enviar al Java de tu compañero:", datos);
  };

  return (
    <div className="container d-flex justify-content-center align-items-center pt-5" style={{ minHeight: '100vh' }}>
      <div className="row">
        <div className="col">
          <div className="card shadow" style={{ width: '30rem' }}>
            <div className="card-body">
              <h5 className="card-title text-center mb-4">Sistema Bancario</h5>
              <h6 className="card-subtitle mb-4 text-muted text-center">
                Crear Nueva Cuenta
              </h6>
              
              {/* Le avisamos al form que cuando se envíe, ejecute handleSubmit */}
              <form onSubmit={handleSubmit}>
                
                <div className="row mb-3">
                  <div className="col">
                    <label htmlFor="nombre" className="form-label">Nombre</label>
                    <input 
                      type="text" 
                      className="form-control" 
                      id="nombre" 
                      name="nombre" 
                      placeholder="Juan" 
                      value={datos.nombre} 
                      onChange={handleChange} 
                    />
                  </div>
                  <div className="col">
                    <label htmlFor="apellido" className="form-label">Apellido</label>
                    <input 
                      type="text" 
                      className="form-control" 
                      id="apellido" 
                      name="apellido"
                      placeholder="Pérez" 
                      value={datos.apellido}
                      onChange={handleChange}
                    />
                  </div>
                </div>

                <div className="mb-3">
                  <label htmlFor="dni" className="form-label">DNI</label>
                  <input 
                    type="number" 
                    className="form-control" 
                    id="dni" 
                    name="dni"
                    placeholder="Sin puntos ni espacios" 
                    value={datos.dni}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input 
                    type="email" 
                    className="form-control" 
                    id="email" 
                    name="email"
                    placeholder="ejemplo@correo.com" 
                    value={datos.email}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="password" className="form-label">Contraseña</label>
                  <input 
                    type="password" 
                    className="form-control" 
                    id="password" 
                    name="password"
                    placeholder="********" 
                    value={datos.password}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-4">
                  <label htmlFor="confirmPassword" className="form-label">Repetir Contraseña</label>
                  <input 
                    type="password" 
                    className="form-control" 
                    id="confirmPassword" 
                    name="confirmPassword"
                    placeholder="********" 
                    value={datos.confirmPassword}
                    onChange={handleChange}
                  />
                </div>

                <div className="d-grid mb-3">
                  <button type="submit" className="btn btn-success">
                    Registrarme
                  </button>
                </div>

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