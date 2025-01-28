import {useState} from "react";

type LinkFormProps = {
    onSubmit: (link: string) => void;
};

export default function LinkForm(props: LinkFormProps) {

    const [link, setLink] = useState<string>("");

    const onSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        props.onSubmit(link);
    }


    return (
        <>
            <form onSubmit={onSubmit}>
                <input
                    type={"text"}
                    placeholder={"Enter a youtube link here..."}
                    value={link}
                    onChange={(event) => setLink(event.target.value)}
                />
                <button type="submit">Summarize</button>
            </form>
        </>
    )
}
