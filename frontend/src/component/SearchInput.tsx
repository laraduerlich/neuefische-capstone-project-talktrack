
type SerachInputProps = {
    value: string;
    onChange: (value: string) => void;
};

export default function SearchInput({value, onChange}: SerachInputProps) {

    return (
        <>
            <div className="relative flex-grow w-full">
                <input
                    type="text"
                    placeholder="Suche..."
                    value={value}
                    onChange={(e) => onChange(e.target.value)}
                    className="w-full h-12 p-3 pl-4 pr-10 text-md bg-transparent border-2 rounded-lg outline-none border-indigo-500 placeholder-indigo-500 focus:ring-2 focus:ring-indigo-600"
                />
            </div>
        </>
    )

}
