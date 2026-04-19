import { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
function Registro() {

  const navigate = useNavigate();

  // Guardamos lo que el usuario escribe
  const [datos, setDatos] = useState({
    nombre: '',
    apellido: '',
    dniCliente: '',
    email: '',
    contrasena: '',
    confirmarContrasena: ''
  });

  //Función que se ejecuta cada vez que el usuario toca una tecla
  const handleChange = (e) => {
    setDatos({
      ...datos, // Copia todo lo que ya estaba guardado
      [e.target.name]: e.target.value // Actualiza solo el campo que se está escribiendo
    });
  };

  //Función que se ejecuta al apretar el botón "Registrarme"
  const handleSubmit = async (e) => {
    e.preventDefault(); // Evita que la página se recargue en blanco

    //validación de contraseñas
   if (datos.contrasena !== datos.confirmPassword) {
      alert("¡Cuidado! Las contraseñas no coinciden.");
      return; // Corta la ejecución acá y no envía nada al servidor
    }

    try {
      // Petición al backend
      // Le mandamos el objeto 'datos' (Axios ignora 'confirmarContrasena' si Java no lo pide)
      const respuesta = await axios.post('http://localhost:8080/auth/register', datos);

      console.log("Servidor responde:", respuesta.data);
      alert("¡Cuenta creada con éxito! Ya podés iniciar sesión.");
      
      // 4. Redirigimos al Login para que entre con su nueva cuenta
      navigate('/');

    } catch (error) {
      console.error("Error al registrar:", error);
      alert("Hubo un error al crear la cuenta. Verifica si ese correo ya existe.");
    }
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
              
              <form onSubmit={handleSubmit}>
                <div className="row mb-3">
                  <div className="col">
                    <label htmlFor="nombre" className="form-label">Nombre</label>
                    <input type="text" className="form-control" id="nombre" name="nombre" placeholder="Juan" value={datos.nombre} onChange={handleChange} required />
                  </div>
                  <div className="col">
                    <label htmlFor="apellido" className="form-label">Apellido</label>
                    <input type="text" className="form-control" id="apellido" name="apellido" placeholder="Pérez" value={datos.apellido} onChange={handleChange} required />
                  </div>
                </div>

                <div className="mb-3">
                  <label htmlFor="dniCliente" className="form-label">DNI</label>
                  <input type="number" className="form-control" id="dniCliente" name="dniCliente" placeholder="Sin puntos ni espacios" value={datos.dniCliente} onChange={handleChange} required />
                </div>

                <div className="mb-3">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input type="email" className="form-control" id="email" name="email" placeholder="ejemplo@correo.com" value={datos.email} onChange={handleChange} required />
                </div>

                <div className="mb-3">
                  <label htmlFor="contrasena" className="form-label">Contraseña</label>
                  <input type="password" className="form-control" id="contrasena" name="contrasena" placeholder="********" value={datos.contrasena} onChange={handleChange} required />
                </div>

                <div className="mb-4">
                  <label htmlFor="confirmPassword" className="form-label">Repetir Contraseña</label>
                  <input type="password" className="form-control" id="confirmPassword" name="confirmPassword" placeholder="********" value={datos.confirmPassword} onChange={handleChange} required />
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