import {useNavigate} from "react-router-dom";
import App from "../App.jsx";

function LandingPage() {
    console.log("LandingPage Loaded");
    const navigate = useNavigate();
    return (
        <div className="landing-container">
            <div className="overlay">
                <h1>Disc Information Storage Center (D.I.S.C.)</h1>
                <p>Use D.I.S.C. to catalog and track all the discs you find on the course!</p>
                <button onClick={() => navigate('/main')}>Enter App</button>
            </div>
        </div>
    );
}

export default LandingPage;