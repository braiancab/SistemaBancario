import { useNavigate } from 'react-router-dom';

function Dashboard() {
  const navigate = useNavigate();

  // Función para cerrar sesión
  const handleCerrarSesion = () => {
    localStorage.removeItem('token'); // Borramos la memoria
    navigate('/'); // Dirige al login
  };

  return (
    <div className="container mt-5">
      <div className="row mb-4">
        <div className="col d-flex justify-content-between align-items-center">
          <h2>Mi Banco</h2>
          <button className="btn btn-outline-danger" onClick={handleCerrarSesion}>
            Cerrar Sesión
          </button>
        </div>
      </div>

      <div className="row">
        <div className="col-md-4">
          <div className="card text-white bg-primary mb-3 shadow">
            <div className="card-body">
              <h5 className="card-title">Saldo Disponible</h5>
              <h2 className="card-text">$ 0.00</h2>
            </div>
          </div>
        </div>

        <div className="col-md-8">
          <div className="card shadow">
            <div className="card-body">
              <h5 className="card-title mb-4">Operaciones Rápidas</h5>
              <div className="d-flex gap-3">
                <button className="btn btn-success btn-lg">Transferir</button>
                <button className="btn btn-secondary btn-lg">Extraer</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;