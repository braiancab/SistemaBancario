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
      </div>

      <ClienteCard cliente={cliente} />
      <CuentaCard cuenta={cuenta} />

     
    </div>
  );
}

export default Dashboard;