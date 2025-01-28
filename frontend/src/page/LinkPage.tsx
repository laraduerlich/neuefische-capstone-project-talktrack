import LinkForm from "../component/LinkForm.tsx";

export default function LinkPage() {

    const handleSubmit = (link: string) => {
        console.log(link);
    };

    return (
        <>
            <p>New Summary</p>
            <LinkForm onSubmit={handleSubmit}/>
        </>
    )
}
