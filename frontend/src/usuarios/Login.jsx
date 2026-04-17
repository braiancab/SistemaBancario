import { Container, Row, Col, Form, Button, Card } from 'react-bootstrap';
export default function Login() {
    return (
        <>
            <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
                <Row>
                    <Col>
                        <Card className="shadow" style={{ width: '25rem' }}>
                            <Card.Body>
                                <Card.Title className="text-center mb-4"> Inicio de Sesión</Card.Title>

                                <Form>
                                    <Form.Group className="mb-3" controlId="formBasicEmail">
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control type="email" placeholder="ejemplo@correo.com" />
                                    </Form.Group>

                                    <Form.Group className="mb-4" controlId="formBasicPassword">
                                        <Form.Label>Contraseña</Form.Label>
                                        <Form.Control type="password" placeholder="********" />
                                    </Form.Group>

                                    <div className="d-grid">
                                        <Button variant="primary" type="submit">
                                            Ingresar
                                        </Button>
                                    </div>
                                </Form>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </>

    )
}
