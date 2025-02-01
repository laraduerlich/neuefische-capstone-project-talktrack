import {FormEvent, useState} from "react";
import axios from "axios";

export default function LinkForm() {

    const [file, setFile] = useState<File | undefined>(undefined);
    const [error, setError] = useState<string | null>(null);

    const handleFileUpload = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!file) {
            alert('Bitte wähle eine Datei aus.');
            return;
        }
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axios.post<{ id: string }>("/api/upload", formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });
            console.log("Upload erfolgreich, Summary ID:", response.data );
        } catch (error: any) {
            console.error("Fehler beim Hochladen:", error);
            setError("Beim Hochladen der Datei ist ein Fehler aufgetreten.");
        }
    }


    return (
        <>
            <form onSubmit={handleFileUpload}>
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
