import java.awt.*
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

fun main() {
    val mainFrame = JFrame("Markdown Viewer").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        size = Dimension(700, 500)
        setLocationRelativeTo(null)
        layout = BorderLayout()

    }

    val textArea = JTextArea().apply {
        isEditable = false
        font = Font("Monospaced", Font.PLAIN, 16)
        foreground = Color(50, 50, 50)
        margin = Insets(10, 10, 10, 10)
    }
    val scrollPane = JScrollPane(textArea)
    scrollPane.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

    val buttonPanel = JPanel().apply {
        layout = FlowLayout(FlowLayout.CENTER, 15, 10)
    }

    val loadButton = JButton("Load Markdown File").apply {
        preferredSize = Dimension(180, 40)
        background = Color(100, 100, 200)
        foreground = Color.WHITE
        font = Font("Arial", Font.BOLD, 14)
    }

    val closeButton = JButton("Close File").apply {
        preferredSize = Dimension(180, 40)
        background = Color(180, 20, 60)
        foreground = Color.WHITE
        font = Font("Arial", Font.BOLD, 14)
        isVisible = false
    }

    loadButton.addActionListener {
        val fileChooser = JFileChooser().apply {
            fileFilter = FileNameExtensionFilter("Markdown Files", "md")
        }
        val returnValue = fileChooser.showOpenDialog(null)

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            if (selectedFile.extension == "md") {
                val content = Files.readAllLines(Paths.get(selectedFile.absolutePath)).joinToString("\n")
                textArea.text = content
                loadButton.isVisible = false
                closeButton.isVisible = true
            } else {
                textArea.text = "Error: Please select a valid Markdown (.md) file."
            }
        }
    }

    closeButton.addActionListener {
        textArea.text = ""
        loadButton.isVisible = true
        closeButton.isVisible = false
    }

    buttonPanel.add(loadButton)
    buttonPanel.add(closeButton)
    mainFrame.add(buttonPanel, BorderLayout.NORTH)
    mainFrame.add(scrollPane, BorderLayout.CENTER)
    mainFrame.isVisible = true
}
