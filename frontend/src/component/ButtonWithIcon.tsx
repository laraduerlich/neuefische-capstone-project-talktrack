
type ButtonWithIconProps = {
    text: string;
    onClick?: () => void;
    className?: string;
    type?: "button" | "submit" | "reset"; // <- type hinzufügen
};

export default function ButtonWithIcon({
                                           text,
                                           onClick,
                                           className = "",
                                           type }: ButtonWithIconProps) {
    return (
        <button
            type={type}
            onClick={onClick}
            className={`px-4 py-3 text-sm font-medium text-white bg-red-500 rounded-lg shadow hover:bg-red-600 flex items-center justify-center space-x-2 ${className}`}
        >
            <span>{text}</span>
        </button>
    );
}
