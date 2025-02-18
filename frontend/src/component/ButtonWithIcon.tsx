
type ButtonWithIconProps = {
    onClick?: () => void;
    icon: string;
    className?: string;
    type?: "button" | "submit" | "reset"; // <- type hinzufügen
};

export default function ButtonWithIcon({
                                           onClick,
                                           icon,
                                           className = "",
                                           type }: ButtonWithIconProps) {
    return (
        <button
            type={type}
            onClick={onClick}
            className={`px-4 py-3 text-sm font-medium text-white bg-indigo-500 rounded-lg shadow hover:bg-indigo-600 flex items-center justify-center space-x-2 ${className}`}>
            <img src={icon} alt="icon" className="w-5 h-5" />
        </button>
    );
}
