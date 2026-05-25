import { useEffect, useState } from "react";
import axios from "axios";

function Tarjetas() {

    const [tarjetas, setTarjetas] = useState([]);
    const [cuentas, setCuentas] = useState([]);

    const [idCuenta, setIdCuenta] = useState("");
    const [numeroTarjeta, setNumeroTarjeta] = useState("");
    const [fechaVencimiento, setFechaVencimiento] = useState("");

    useEffect(() => {
        cargarTarjetas();
        cargarCuentas();
    }, []);

    const cargarTarjetas = async () => {
        try {
            const response = await axios.get(
                "http://localhost:8080/api/tarjetas"
            );

            setTarjetas(response.data);

        } catch (error) {
            console.error(error);
        }
    };

    const cargarCuentas = async () => {
        try {

            const usuario = JSON.parse(
                localStorage.getItem("usuario")
            );

            const response = await axios.get(
                `http://localhost:8080/api/cuentas/cliente/${usuario.idCliente}`
            );

            setCuentas(response.data);

        } catch (error) {
            console.error(error);
        }
    };

    const generarNumeroTarjeta = () => {

        let numero = "";

        for (let i = 0; i < 16; i++) {
            numero += Math.floor(Math.random() * 10);
        }

        return numero;
    };

    const agregarTarjeta = async (e) => {

        e.preventDefault();

        try {

            const nuevaTarjeta = {
                numeroTarjeta: generarNumeroTarjeta(),
                fechaVencimiento,
                estado: "ACTIVA",
                cuenta: {
                    idCuenta: idCuenta
                }
            };

            await axios.post(
                "http://localhost:8080/api/tarjetas",
                nuevaTarjeta
            );

            alert("Tarjeta creada correctamente");

            setFechaVencimiento("");
            setIdCuenta("");

            cargarTarjetas();

        } catch (error) {

            console.error(error);
            alert("Error al crear tarjeta");
        }
    };

    const cambiarEstado = async (tarjeta) => {

        try {

            const tarjetaActualizada = {
                ...tarjeta,
                estado:
                    tarjeta.estado === "ACTIVA"
                        ? "INACTIVA"
                        : "ACTIVA"
            };

            await axios.put(
                `http://localhost:8080/api/tarjetas/${tarjeta.idTarjeta}`,
                tarjetaActualizada
            );

            cargarTarjetas();

        } catch (error) {
            console.error(error);
        }
    };

    const ocultarNumero = (numero) => {

        return "**** **** **** " + numero.slice(-4);
    };

    return (
        <div className="container mt-4">

            <h2>Mis Tarjetas</h2>

            <div className="card p-3 mb-4">
                <h4>Solicitar Tarjeta</h4>

                <form onSubmit={agregarTarjeta}>

                    <div className="mb-3">
                        <label>Cuenta asociada</label>

                        <select
                            className="form-control"
                            value={idCuenta}
                            onChange={(e) =>
                                setIdCuenta(e.target.value)
                            }
                            required
                        >
                            <option value="">
                                Seleccione una cuenta
                            </option>

                            {cuentas.map((cuenta) => (
                                <option
                                    key={cuenta.idCuenta}
                                    value={cuenta.idCuenta}
                                >
                                    {cuenta.cbu}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="mb-3">
                        <label>Fecha Vencimiento</label>

                        <input
                            type="month"
                            className="form-control"
                            value={fechaVencimiento}
                            onChange={(e) =>
                                setFechaVencimiento(e.target.value)
                            }
                            required
                        />
                    </div>

                    <button
                        className="btn btn-success"
                        type="submit"
                    >
                        Crear Tarjeta
                    </button>

                </form>
            </div>

            <div className="row">

                {tarjetas.map((tarjeta) => (

                    <div
                        key={tarjeta.idTarjeta}
                        className="col-md-4 mb-3"
                    >
                        <div className="card shadow">

                            <div className="card-body">

                                <h5>
                                    {ocultarNumero(
                                        tarjeta.numeroTarjeta
                                    )}
                                </h5>

                                <p>
                                    <strong>Vence:</strong>{" "}
                                    {tarjeta.fechaVencimiento}
                                </p>

                                <p>
                                    <strong>Estado:</strong>{" "}
                                    {tarjeta.estado}
                                </p>

                                <button
                                    className={
                                        tarjeta.estado ===
                                        "ACTIVA"
                                            ? "btn btn-danger"
                                            : "btn btn-primary"
                                    }
                                    onClick={() =>
                                        cambiarEstado(tarjeta)
                                    }
                                >
                                    {tarjeta.estado ===
                                    "ACTIVA"
                                        ? "Desactivar"
                                        : "Activar"}
                                </button>

                            </div>

                        </div>
                    </div>

                ))}

            </div>

        </div>
    );
}

export default Tarjetas;