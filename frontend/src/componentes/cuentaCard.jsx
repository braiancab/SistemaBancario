import { useState } from "react";

const cuentaCard = ({ cuenta }) => {
    const [mostrarSaldo, setMostrarSaldo] = useState(false);
  if (!cuenta) return null;

  return (
    <div className="card shadow border-0 mb-4">
      <div className="card-header bg-dark text-white">
        <h4>Mi Cuenta</h4>
      </div>

      <div className="card-body">
        <p><strong>N° Cuenta:</strong> {cuenta.idCuenta}</p>
        <p><strong>CVU:</strong> {cuenta.cvu}</p>
        <p><strong>Alias:</strong> {cuenta.alias}</p>
        <p><strong>Tipo:</strong> {cuenta.tipoCuenta?.tipo}</p>
         <p className="text-success fw-bold">
          <strong>Saldo:</strong>{" "}
          {mostrarSaldo ? `$ ${cuenta.saldo}` : "••••••"}
        </p>
          <button
          className="btn btn-outline-primary btn-sm"
          onClick={() => setMostrarSaldo(!mostrarSaldo)}
        >
          {mostrarSaldo ? "Ocultar Saldo" : "Ver Saldo"}
        </button>
      </div>
    </div>
  );
};

export default cuentaCard;