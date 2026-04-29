import { useNavigate } from "react-router-dom";
import { useDashboard } from "../hooks/useDashboard";
import ClienteCard from "../componentes/clienteCard";
import CuentaCard from "../componentes/cuentaCard";

function Dashboard() {
  const navigate = useNavigate();
  const { cliente, cuenta } = useDashboard(navigate);

  const handleCerrarSesion = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="container mt-5">

      <div className="d-flex justify-content-between mb-4">
        <h2>Mi Banco</h2>
        <button className="btn btn-danger" onClick={handleCerrarSesion}>
          Cerrar sesión
        </button>
      </div>

      <ClienteCard cliente={cliente} />
      <CuentaCard cuenta={cuenta} />

      <div className="d-flex gap-3">
        <button className="btn btn-success" onClick={() => navigate("/transferencias")}>
          Transferir
        </button>

        <button className="btn btn-secondary">
          Extraer
        </button>

        <button className="btn btn-primary">
          Depositar
        </button>
      </div>
    </div>
  );
}

export default Dashboard;