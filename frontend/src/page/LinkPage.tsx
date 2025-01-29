import LinkForm from "../component/LinkForm.tsx";
import axios from "axios";

export default function LinkPage() {

    const handleUpload = (file: FormData) => {
        axios.post("/api/upload", file)
        .then(response => console.log(response))
    };

    return (
        <>
            <p>New Summary</p>
            <LinkForm uploadFile={handleUpload}/>
        </>
    )
}
