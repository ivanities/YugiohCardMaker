package images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ImageSplitter{
    private final BufferedImage base_image;
    private final int separator;

    public ImageSplitter(BufferedImage base_image, int separator){
        this.base_image = base_image;
        this.separator = separator;
    }
    
    public BufferedImage[] split() throws IOException{
        return split("");
    }

    public BufferedImage[] split(String file_path) throws IOException{
        int start_y = 0;

        ArrayList rectangles = new ArrayList();
        ArrayList current_row;

        while(true){
            if(start_y >= base_image.getHeight()){
                break;
            }
            current_row = getRow(start_y);
            if(current_row.isEmpty()){
                break;
            }
            rectangles.addAll(current_row);
            start_y += getMaxHeight(current_row) + 1;
        }

        BufferedImage[] split_result = new BufferedImage[rectangles.size()];

        for(int i = 0; i < rectangles.size(); i++){
            Rectangle rec = ((Rectangle)rectangles.get(i));
            split_result[i] = base_image.getSubimage(rec.x, rec.y, rec.width, rec.height);
//            ImageIO.write(split_result[i], "PNG", new File(file_path + "\\split_result_" + i + ".png"));
        }
        return split_result;
    }

    public ArrayList getRow(int start_y){
        ArrayList rectangles = new ArrayList();

        for(int x = 0; x < this.base_image.getWidth(); x++){
            Rectangle current_rectangle = getRectangle(x, start_y);

            if(current_rectangle == null){
                break;
            }else{
                rectangles.add(current_rectangle);
                x = current_rectangle.x + current_rectangle.width;
            }
        }
        return rectangles;
    }

    public Rectangle getRectangle(int start_x, int start_y){
        for(int x = start_x; x < this.base_image.getWidth(); x++){
            if(this.base_image.getRGB(x, start_y) == this.separator){

                --x;
                for(int y = start_y; y < this.base_image.getHeight(); y++){
                    if(this.base_image.getRGB(x, y) == this.separator){

                        --y;
                        return new Rectangle(start_x, start_y, (x + 1) - start_x, (y + 1) - start_y);
                    }
                }
            }
        }
        return null;
    }

    private int getMaxHeight(ArrayList rectangles){
        int max_height = 0;

        for(int i = 0; i < rectangles.size(); i++){
            int current_height = ((Rectangle)rectangles.get(i)).height;
            if(max_height < current_height){
                max_height = current_height;
            }
        }
        return max_height;
    }
}
