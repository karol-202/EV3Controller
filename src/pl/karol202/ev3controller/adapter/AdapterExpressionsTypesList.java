package pl.karol202.ev3controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.command.CommandType;
import pl.karol202.ev3controller.expression.ExpressionType;

import java.util.ArrayList;

public class AdapterExpressionsTypesList extends ArrayAdapter<ExpressionType>
{
    private Context context;

    public AdapterExpressionsTypesList(Context context, ArrayList<ExpressionType> items)
    {
        super(context, R.layout.list_expressions_row, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_expressions_row, parent, false);
        } else view = convertView;
        ImageView imageViewExpression = (ImageView) view.findViewById(R.id.image_expression);
        TextView textViewExpressionName = (TextView) view.findViewById(R.id.text_expression_name);
        imageViewExpression.setImageResource(getItem(position).getIcon());
        textViewExpressionName.setText(getItem(position).getName());
        return view;
    }
}
