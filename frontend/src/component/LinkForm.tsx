import {FormEvent, useState} from "react";
import axios from "axios";

// type LinkFormProps = {
//     uploadFile: (file: FormData) => void;
// };

export default function LinkForm() {

    const [file, setFile] = useState<File | undefined>(undefined);

    const handleFileUpload = (event: FormEvent<HTMLFormElement>) => {
        event.isDefaultPrevented()
        if (!file) {
            alert('Bitte wähle eine Datei aus.');
            return;
        }
        const formData = new FormData();
        formData.append('file', file);

        axios.post("/api/upload", file, {
            headers: { 'Content-Type': 'multipart/form-data' },
            timeout: 60000, // Erhöht das Timeout auf 60 Sekunden
        })
            .then(response => console.log(response))
            .catch(error => {console.error(error)});
    }


    return (
        <>
            <form onSubmit={handleFileUpload}>
                <input
                    type={"file"}
                    accept={"audio/mp3, audio/mp4, audio/m4a"}
                    onChange={(event) => setFile(event.target.files?.[0])}
                />
                <button >Hochladen</button>
            </form>
        </>
    )
}
