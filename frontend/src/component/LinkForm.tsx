import {FormEvent, useState} from "react";

type LinkFormProps = {
    uploadFile: (file: FormData) => void;
};

export default function LinkForm(props: LinkFormProps) {

    const [file, setFile] = useState<File | undefined>(undefined);

    const handleFileUpload = (event: FormEvent<HTMLFormElement>) => {
        event.isDefaultPrevented()
        if (!file) {
            alert('Bitte wähle eine Datei aus.');
            return;
        }
        const formData = new FormData();
        formData.append('file', file);

        props.uploadFile(formData)
    }


    return (
        <>
            <form onSubmit={handleFileUpload}>
                <input
                    type={"file"}
                    accept={"audio/mp3"}
                    onChange={(event) => setFile(event.target.files?.[0])}
                />
                <button >Hochladen</button>
            </form>
        </>
    )
}
