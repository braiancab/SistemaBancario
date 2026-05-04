const cuentaCard = ({ cuenta }) => {
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
          <strong>Saldo:</strong> $ {cuenta.saldo}
        </p>
      </div>
    </div>
  );
};

export default cuentaCard;