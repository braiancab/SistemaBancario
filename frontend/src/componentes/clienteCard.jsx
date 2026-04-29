const clienteCard = ({ cliente }) => {
  if (!cliente) return null;

  return (
    <div className="card shadow border-0 mb-4">
      <div className="card-header bg-primary text-white">
        <h4>Datos del Cliente</h4>
      </div>

      <div className="card-body">
        <p><strong>Nombre:</strong> {cliente.nombre}</p>
        <p><strong>Apellido:</strong> {cliente.apellido}</p>
        <p><strong>DNI:</strong> {cliente.dni}</p>
        <p><strong>Email:</strong> {cliente.email}</p>
      </div>
    </div>
  );
};

export default clienteCard;