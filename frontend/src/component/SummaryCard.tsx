import {Summary} from "../type/Summary.tsx";
import ButtonWithIcon from "./ButtonWithIcon.tsx";
import html2canvas from "html2canvas";
import jsPDF, {ImageProperties} from "jspdf";
import {useRef} from "react";

export type SummaryCardProps = {
    summary: Summary | null,
    onClick: () => void,
}

export default function SummaryCard({summary, onClick}: SummaryCardProps){

    const printRef = useRef(null)

    if (summary?.text === undefined) {
        return console.error("Keine Zusammenfassung gefunden!");
    }

    // String in Array to separate the different bullet points
    const summaryPoints: string[] = summary?.text.split("\n");

    // Button handler to edit summary
    const handleEditButtonClick = () => {
        onClick();
    }

    // Button handler to generate a PDF
    const handlePDFButtonClick = async () => {
        const element = printRef.current;
        if (!element) {
            return;
        }

        const canvas: HTMLCanvasElement = await html2canvas(element);
        const data: string = canvas.toDataURL("image/png");

        const pdf = new jsPDF({
            orientation: "portrait",
            unit: "px",
            format: "a4",
        });

        const imgProperties: ImageProperties = pdf.getImageProperties(data);
        const pdfWidth: number = pdf.internal.pageSize.getWidth();

        const pdfHeight: number = (imgProperties.height * pdfWidth) / imgProperties.width;

        pdf.addImage(data, "PNG", 0, 0, pdfWidth, pdfHeight)
        pdf.save("summary.pdf");

    }

    return (
        <>
            <div className="flex space-x-4" ref={printRef}>
                <fieldset className="w-full p-4 space-y-4 border border-gray-300 rounded-md dark:text-gray-800">
                    <legend className="text-2xl font-bold">{summary?.title}</legend>
                    <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between p-6 bg-white/40 rounded-md backdrop-blur-md dark:text-gray-800">
                        <ul>
                            {summaryPoints.map((point, index) => (
                                <li key={index}>{point}</li>
                            ))}
                        </ul>
                    </div>
                </fieldset>
            </div>
            <div className="flex space-x-4 mt-4">
                <ButtonWithIcon
                    icon={"/edit-icon.png"}
                    onClick={handleEditButtonClick}
                />
                <ButtonWithIcon
                    onClick={handlePDFButtonClick}
                    icon={"/pdf-icon.png"}
                />
            </div>
        </>
    )
}
