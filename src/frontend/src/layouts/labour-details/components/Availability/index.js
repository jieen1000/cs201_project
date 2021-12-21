import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import { addDays } from 'date-fns';
import { useState } from 'react';
import { DateRangePicker } from 'react-date-range';

/**
 * {@code Availability} creates the date range picker component.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default function BasicDateRangePicker({onChangeDate}) {
    const [state, setState] = useState({
        selection: {
          startDate: new Date(),
          endDate: new Date(),
          key: 'selection'
        }
      });
    
    return (
      <DateRangePicker
      onChange={item => {
        setState({ ...state, ...item });
      }}
      months={1}
      minDate={addDays(new Date(), 0)}
      maxDate={addDays(new Date(), 30)}
      direction="vertical"
      scroll={{ enabled: true }}
      ranges={[state.selection]}
      onChangeDate={onChangeDate(state.selection)}
    />
    );
}