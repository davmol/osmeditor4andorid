package de.blau.android.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import de.blau.android.R;
import de.blau.android.util.ThemeUtils;

public class TextLineDialog {

    /**
     * Create a Dialog with one EditText
     * 
     * @param ctx the ANdroid Context
     * @param titleId the string resource for the title
     * @param listener a TextLineInterface listener provided by the caller
     * @return an AlertDialog instance
     */
    public static AlertDialog get(@NonNull Context ctx, int titleId, @NonNull TextLineInterface listener) {
        // inflater needs to be got from a themed view or else all our custom stuff will not style correctly
        final LayoutInflater inflater = ThemeUtils.getLayoutInflater(ctx);

        Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(titleId);

        View layout = inflater.inflate(R.layout.text_line, null);
        builder.setView(layout);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText input = layout.findViewById(R.id.text_line_edit);
                listener.processLine(input);
            }
        });

        return builder.create();
    }

    public interface TextLineInterface {
        /**
         * Do something with the text entered by the user
         * 
         * @param input the EditTExt
         */
        void processLine(@Nullable final EditText input);
    }
}
