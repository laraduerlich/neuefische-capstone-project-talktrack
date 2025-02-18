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
            <form onSubmit={handleFileUpload} className="space-y-4">
                <fieldset className="w-full p-4 space-y-4 border border-gray-300 rounded-md dark:text-gray-800">
                    <legend className="text-2xl font-bold">Neue Zusammenfassung:</legend>
                    <div className="relative rounded-md border bg-white/40 backdrop-blur-md border-gray-300">
                    <input
                        id="title"
                        name="title"
                        type={"text"}
                        placeholder={"Titel der Zusammenfassung..."}
                        onChange={(event) => setTitle(event.target.value)}
                        className="w-full py-2 pl-3 text-sm rounded-md bg-white/40 backdrop-blur-md focus:outline-none dark:bg-gray-100 dark:text-gray-800" />
                    </div>
                    <input
                        type={"file"}
                        accept={"audio/mp3, audio/mp4, audio/m4a"}
                        onChange={(event) => setFile(event.target.files?.[0] || undefined)}
                        className="py-2 pl-3 text-sm rounded-md focus:outline-none dark:bg-gray-100 dark:text-gray-800 focus:dark:bg-gray-50"
                    />
                    <button type="submit" className="sm:w-auto h-12 px-5 py-3 text-sm font-semibold text-white bg-indigo-500 rounded-lg shadow-md hover:bg-indigo-600 flex items-center justify-center space-x-2 min-w-[200px]">Hochladen</button>
                </fieldset>
            </form>
        </>
    )
}
