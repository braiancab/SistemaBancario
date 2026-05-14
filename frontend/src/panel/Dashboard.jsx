import { useNavigate } from "react-router-dom";
import { useDashboard } from "../hooks/useDashboard";
import ClienteCard from "../componentes/clienteCard";
import CuentaCard from "../componentes/cuentaCard";

function Dashboard() {
  const navigate = useNavigate();
  const { cliente, cuentas, recargar }= useDashboard(navigate);

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
      <div className="row">
        {cuentas && cuentas.length > 0 ? (
          cuentas.map((cuenta) => (
            <div className="col-md-6" key={cuenta.idCuenta}> 
              <CuentaCard cuenta={cuenta} />
            </div>
          ))
        ) : (
          <div className="col-12 text-center mt-4">
            <p className="text-muted fw-medium">Todavía no tenés cuentas creadas.</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default Dashboard;
