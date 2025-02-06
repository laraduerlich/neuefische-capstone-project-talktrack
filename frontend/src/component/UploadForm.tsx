import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {createSummary} from "../utils/dataService.ts";

export default function UploadForm() {

    const [file, setFile] = useState<File | undefined>(undefined);
    const [title, setTitle] = useState<string>('');
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate();

    const handleFileUpload = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!file) {
            alert('Bitte wähle eine Datei aus.');
            return;
        }
        if (!title) {
            alert('Bitte gebe einen Titel ein.');
            return;
        }

        setLoading(true)
        const formData = new FormData();
        formData.append("file", file);
        formData.append("title", title);

        try {
            const id = await createSummary(formData); // Backend dauert 1–2 Min.

            if (!id || id === "Kein ID gefunden") {
                alert("Fehler beim Hochladen. Keine ID erhalten.");
                return;
            }

            navigate("/summary/" + id);
        } catch (error) {
            console.error("Fehler beim Hochladen:", error);
            alert("Ein Fehler ist aufgetreten. Bitte versuche es erneut.");
        } finally {
            setLoading(false); // Ladeanzeige wieder ausblenden
        }
    }

    if (loading) return <p>Daten werden hochgeladen...</p>;

    return (
        <>
            <form onSubmit={handleFileUpload}>
                <input
                    type={"text"}
                    placeholder={"Titel der Zusammenfassung..."}
                    onChange={(event) => setTitle(event.target.value)}
                />
                <input
                    type={"file"}
                    accept={"audio/mp3, audio/mp4, audio/m4a"}
                    onChange={(event) => setFile(event.target.files?.[0] || undefined)}
                />
                <button >Hochladen</button>
            </form>
        </>
    )
}
