import os


def concatenate_asciidoc(folder_name, output_file='.concat_final_doc.asciidoc'):
    """
    Concatenate asciidoc files from the specified folder into one file.

    :param folder_name: str, The name of the folder containing asciidoc files.
    :param output_file: str, The name of the resulting file. Default is 'concatenated.asciidoc'.
    :return: None
    """

    # List to store the contents of the asciidoc files
    concatenated_content = []

    try:
        # Check if folder exists
        if not os.path.exists(folder_name):
            print(f"Error: The folder {folder_name} does not exist.")
            return

        # Get the list of files from the specified folder
        files = os.listdir(folder_name)

        # Filter out the files with the extensions .asciidoc or .adoc
        asciidoc_files = [f for f in files if (f.endswith('.asciidoc') or f.endswith('.adoc')) and not f.startswith('.')]

        # Sort the list of files
        asciidoc_files.sort()

        # Read the content of each asciidoc file and store it in xxthe list
        for file in asciidoc_files:
            with open(os.path.join(folder_name, file), 'r') as f:
                content = f.read()
                concatenated_content.append(content)
                concatenated_content.append("<<< ")

        # Check if there were any AsciiDoc files processed
        if not concatenated_content:
            print(f"No AsciiDoc files found in the directory: {folder_name}")
            return

        # Write the content to the specified output file
        with open(output_file, 'w') as f:
            for content in concatenated_content:
                f.write(content + '\n\n')  # add a couple of newlines between files for clarity

        print(f"Concatenation complete. Output file: {output_file}")

    except OSError as e:
        print(f"An error occurred: {e}")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")


if __name__ == "__main__":
    folder_name = "/Users/andrii/git/my-projects/structurizr-c4model-extension/src/test/resources/documentation/demo-system_container"
    concatenate_asciidoc(folder_name)
