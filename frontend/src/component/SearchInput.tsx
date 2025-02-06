
type SerachInputProps = {
    value: string;
    onChange: (value: string) => void;
};

export default function SearchInput({value, onChange}: SerachInputProps) {

    return (
        <>
            <div>
                <input
                    type="text"
                    placeholder="Suche..."
                    value={value}
                    onChange={(e) => onChange(e.target.value)}
                />
            </div>
        </>
    )

}
