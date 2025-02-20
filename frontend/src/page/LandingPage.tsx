import {useNavigate} from "react-router-dom";

export default function LandingPage() {

    const navigate = useNavigate();

    const handleGoButtonClick = () => {
        navigate("/summary/new");
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-[60vh]">
            <h2 className="p-6 backdrop-blur-md text-7xl text-center">
                Welcome to TalkTrack
            </h2>

            <button
                onClick={handleGoButtonClick}
                className="mt-4 w-full sm:w-auto h-12 px-5 py-3 text-sm font-semibold text-white bg-indigo-500 rounded-lg shadow-md hover:bg-indigo-600 flex items-center justify-center min-w-[200px]"
            >
                <span>Neue Zusammenfassung erstellen</span>
            </button>
        </div>
    )
}
